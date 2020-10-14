package edu.zju.gis.dldsj.server.utils;

import edu.zju.gis.dldsj.server.entity.ParallelModelWithBLOBs;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 用于读取模型注册文件
 *
 * @author yanlo yanlong_lee@qq.com
 * @version 1.0 2018/08/01
 */
public final class XMLReader {
    public static ParallelModelWithBLOBs readParallelModel(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
        return readParallelModel(new File(xmlFile));
    }

    public static ParallelModelWithBLOBs readParallelModel(File xmlFile) throws IOException, ParserConfigurationException, SAXException {
        if (!xmlFile.exists())
            throw new FileNotFoundException(String.format("file [%s] not found.", xmlFile.getAbsolutePath()));
        ParallelModelWithBLOBs parallelModel = new ParallelModelWithBLOBs();
        // 将XML转化为DOM文件
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
        // 得到一个element根元素
        Element element = doc.getDocumentElement();
        if (!element.getNodeName().equalsIgnoreCase("parallelModel")) {
            throw new RuntimeException("this is not a valid parallel model template file.");
        } else {
            // 获得根元素下的子节点
            NodeList childNodes = element.getChildNodes();
            // 遍历这些子节点
            for (int i = 0; i < childNodes.getLength(); i++) {
                //获得每个对应位置的节点
                Node node = childNodes.item(i);
                if (node.getNodeName().equals("#text") || node.getNodeName().equals("#comment"))
                    continue;
                String nodeValue = node.hasChildNodes() ? node.getFirstChild().getNodeValue().trim() : "";
                switch (node.getNodeName()) {
                    case "artifactId":
                        parallelModel.setArtifactId(nodeValue);
                        break;
                    case "name":
                        parallelModel.setName(nodeValue);
                        break;
                    case "description":
                        parallelModel.setDescription(nodeValue);
                        break;
                    case "algo":
                        parallelModel.setAlgo(nodeValue);
                        break;
                    case "usage":
                        parallelModel.setUsages(nodeValue);
                        break;
                    case "mainClass":
                        parallelModel.setMainClass(nodeValue);
                        break;
                    case "frameworkType":
                        parallelModel.setFrameworkType(nodeValue);
                        break;
                    case "date":
                        parallelModel.setDate(nodeValue);
                        break;
                    case "versionId":
                        parallelModel.setVersionId(nodeValue);
                        break;
                    case "keyWords":
                        parallelModel.setKeywords(nodeValue);
                        break;
                    case "groupId":
                        parallelModel.setGroupId(nodeValue);
                        break;
                    case "authorId":
                        parallelModel.setAuthorId(nodeValue);
                        break;
                    case "email":
                        parallelModel.setEmail(nodeValue);
                        break;
                    case "in":
                        parallelModel.setInput(readInOrOut(node).toString());
                        break;
                    case "out":
                        parallelModel.setOut(readInOrOut(node).toString());
                        break;
                    case "parameters":
                        parallelModel.setParameters(readParameters(node).toString());
                        break;
                    case "packages":
                        parallelModel.setPackages(readPackages(node).toString());
                        break;
                    case "interfaces":
                        parallelModel.setInterfaces(readInterfaces(node).toString());
                        break;
                    case "test":
                        parallelModel.setTest(readTest(node).toString());
                        break;
                    case "num-executors":
                        parallelModel.setNumExecutors(Integer.parseInt(nodeValue));
                        break;
                    case "driver-memory":
                        parallelModel.setDriverMemory(nodeValue);
                        break;
                    case "executor-memory":
                        parallelModel.setExecutorMemory(nodeValue);
                        break;
                    case "executor-cores":
                        parallelModel.setExecutorCores(Integer.parseInt(nodeValue));
                        break;
                    case "parallelism":
                        parallelModel.setParallelism(Integer.parseInt(nodeValue));
                        break;
                    default:
                        break;
                }
            }
        }
        return parallelModel;
    }

    /**
     * 读取&lt;in&gt;,&lt;out&gt;标签内容
     */
    private static JSONArray readInOrOut(Node node) {
        JSONArray in = new JSONArray();
        NodeList dataNodes = node.getChildNodes();
        for (int i = 0; i < dataNodes.getLength(); i++) {
            Node dataNode = dataNodes.item(i);
            if (dataNode.getNodeName().equals("data")) {
                JSONObject data = new JSONObject();
                NodeList childNodes = dataNode.getChildNodes();
                for (int k = 0; k < childNodes.getLength(); k++) {
                    Node childNode = childNodes.item(k);
                    if (childNode.getNodeName().equals("#text") || childNode.getNodeName().equals("#comment"))
                        continue;
                    if (childNode.getNodeName().equalsIgnoreCase("fields")) {
                        NodeList properties = childNode.getChildNodes();
                        data.put(childNode.getNodeName(), getProperties(properties));
                    } else
                        data.put(childNode.getNodeName(), childNode.getFirstChild().getNodeValue().trim());
                }
                in.put(data);
            }
        }
        return in;
    }

    /**
     * 读取&lt;parameters&gt;标签内容
     */
    private static JSONArray readParameters(Node node) {
        JSONArray parameters = new JSONArray();
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            //将#TEXT的子节点过滤掉
            if (child.getNodeName().equals("parameter")) {
                NamedNodeMap attributes = child.getAttributes();
                JSONObject property = new JSONObject();
                for (int k = 0; k < attributes.getLength(); k++) {
                    property.put(attributes.item(k).getNodeName(), attributes.item(k).getFirstChild().getNodeValue().trim());
                }
                parameters.put(property);
            }
        }
        return parameters;
    }

    /**
     * 读取&lt;packages&gt;标签内容
     */
    private static JSONArray readPackages(Node node) {
        JSONArray packages = new JSONArray();
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            if (child.getNodeName().equals("package")) {
                JSONObject packageItem = new JSONObject();
                NodeList packageNodes = child.getChildNodes();
                for (int k = 0; k < packageNodes.getLength(); k++) {
                    Node packageNode = packageNodes.item(k);
                    if (packageNode.getNodeName().equals("#text") || packageNode.getNodeName().equals("#comment"))
                        continue;
                    if (packageNode.getFirstChild() != null)
                        packageItem.put(packageNode.getNodeName(), packageNode.getFirstChild().getNodeValue().trim());
                }
                if (packageItem.length() > 0)//去除空标签
                    packages.put(packageItem);
            }
        }
        return packages;
    }

    /**
     * 读取&lt;interfaces&gt;标签内容
     */
    private static JSONArray readInterfaces(Node node) {
        JSONArray interfaces = new JSONArray();
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            if (child.getNodeName().equals("interface")) {
                JSONObject interfaceItem = new JSONObject();
                NodeList interfaceNodes = child.getChildNodes();
                for (int k = 0; k < interfaceNodes.getLength(); k++) {
                    Node interfaceNode = interfaceNodes.item(k);
                    if (interfaceNode.getNodeName().equals("#text") || interfaceNode.getNodeName().equals("#comment"))
                        continue;
                    if (interfaceNode.getNodeName().equalsIgnoreCase("inParams")
                            || interfaceNode.getNodeName().equalsIgnoreCase("outParams")) {
                        NodeList properties = interfaceNode.getChildNodes();
                        interfaceItem.put(interfaceNode.getNodeName(), getProperties(properties));
                    } else {
                        if (interfaceNode.getFirstChild() != null)
                            interfaceItem.put(interfaceNode.getNodeName(), interfaceNode.getFirstChild().getNodeValue().trim());
                    }
                }
                if (interfaceItem.length() > 0)//去除空标签
                    interfaces.put(interfaceItem);
            }
        }
        return interfaces;
    }

    /**
     * 读取&lt;test&gt;标签内容
     */
    private static JSONObject readTest(Node node) {
        JSONObject test = new JSONObject();
        NodeList nodes = node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node child = nodes.item(i);
            if (child.getFirstChild() != null)
                test.put(child.getNodeName(), child.getFirstChild().getNodeValue().trim());
        }
        return test;
    }

    private static JSONArray getProperties(NodeList properties) {
        JSONArray array = new JSONArray();
        for (int t = 0; t < properties.getLength(); t++) {
            Node property = properties.item(t);
            if (property.getNodeName().equalsIgnoreCase("property")) {
                NamedNodeMap attributes = property.getAttributes();
                JSONObject field = new JSONObject();
                for (int x = 0; x < attributes.getLength(); x++) {
                    field.put(attributes.item(x).getNodeName(), attributes.item(x).getFirstChild().getNodeValue().trim());
                }
                array.put(field);
            }
        }
        return array;
    }
}
