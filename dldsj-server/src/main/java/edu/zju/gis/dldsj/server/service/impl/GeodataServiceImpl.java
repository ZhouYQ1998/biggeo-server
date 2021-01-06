package edu.zju.gis.dldsj.server.service.impl;

import com.github.pagehelper.PageHelper;
import edu.zju.gis.dldsj.server.base.BaseServiceImpl;
import edu.zju.gis.dldsj.server.common.Page;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.config.CommonSetting;
import edu.zju.gis.dldsj.server.constant.CodeConstants;
import edu.zju.gis.dldsj.server.entity.Geodata;
import edu.zju.gis.dldsj.server.entity.GeodataItem;
import edu.zju.gis.dldsj.server.entity.vo.VizData;
import edu.zju.gis.dldsj.server.mapper.mysql.GeodataItemMapper;
import edu.zju.gis.dldsj.server.mapper.mysql.GeodataMapper;
import edu.zju.gis.dldsj.server.mapper.pg.TileMapper;
import edu.zju.gis.dldsj.server.service.GeodataService;
import edu.zju.gis.dldsj.server.utils.GeometryUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Jiarui 2020/8/26
 * @author zjh 2020/10/12
 * @update zyq 2020/11/4
 */
@Service
public class GeodataServiceImpl extends BaseServiceImpl<GeodataMapper, Geodata, String> implements GeodataService {

    @Autowired
    private GeodataMapper geodataMapper;

    @Autowired
    private GeodataItemMapper geodataItemMapper;

    @Autowired
    private TileMapper tileMapper;

    @Autowired
    private CommonSetting setting;

    // 数据目录功能：根据type字段返回数据
    public Result<Page<Geodata>> selectByType1(String type, Page<Geodata> page) {
        Result<Page<Geodata>> result = new Result<>();
        try{
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
            List<Geodata> geodataList = geodataMapper.selectByType1(type);
            Page<Geodata> geodataPage = new Page<>(geodataList);
            if(geodataList.size() != 0){
                result.setCode(CodeConstants.SUCCESS).setBody(geodataPage).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    public Result<Page<Geodata>> selectByType2(String type, Page<Geodata> page) {
        Result<Page<Geodata>> result = new Result<>();
        try{
            PageHelper.startPage(page.getPageNo(), page.getPageSize());
            List<Geodata> geodataList = geodataMapper.selectByType2(type);
            Page<Geodata> geodataPage = new Page<>(geodataList);
            if(geodataList.size() != 0){
                result.setCode(CodeConstants.SUCCESS).setBody(geodataPage).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    // 数据目录功能：根据type字段返回唯一不同值与对应数量
    public Result<Map<String, String>> getDistinctField(String field, Page<Geodata> page) {
        Result<Map<String, String>> result = new Result<>();
        try{
            Map<String, String> map = new HashMap<>();
            String str = "";
            List<Geodata> list = geodataMapper.getDistinctField(field);
            for (Geodata s : list) {
                switch (field) {
                    case "TYPE_1":
                        str = s.getType1();
                        break;
                    case "TYPE_2":
                        str = s.getType2();
                        break;
                }
                if(str != ""){
                    String count = geodataMapper.getCountOfField(field, str);
                    map.put(str, count);
                }
            }
            if(map.size() != 0){
                result.setCode(CodeConstants.SUCCESS).setBody(map).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    // 查询下载数量最多的5条数据
    public Result<List<Geodata>> getPopularData() {
        Result<List<Geodata>> result = new Result<>();
        try{
            List<Geodata> geodataList = geodataMapper.getPopularData();
            if(geodataList.size() != 0){
                result.setCode(CodeConstants.SUCCESS).setBody(geodataList).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    // 查询数据集详情
    public Result<List<GeodataItem>> getDatail(String id){
        Result<List<GeodataItem>> result = new Result<>();
        try{
            List<GeodataItem> geodataList = geodataItemMapper.getDatail(id);
            if(geodataList.size() != 0){
                geodataMapper.viewTimesPlus(id);
                result.setCode(CodeConstants.SUCCESS).setBody(geodataList).setMessage("查询成功");
            }
            else{
                result.setCode(CodeConstants.USER_NOT_EXIST).setMessage("查询失败：实体不存在");
            }
        }catch (RuntimeException e){
            result.setCode(CodeConstants.SERVICE_ERROR).setMessage("查询失败：" + e.getMessage());
        }
        return result;
    }

    @Override
    public String getWholePathOfDataItem(String id) {
        GeodataItem geodataItem = geodataItemMapper.selectByPrimaryKey(id);
        Geodata geodata = geodataMapper.selectByPrimaryKey(geodataItem.getDataset());
        return geodata.getPath() + "/" + geodataItem.getTitle() + geodataItem.getFormat();
    }

    @Override
    public VizData initVizData(String path) {
        VizData vizData = new VizData();
        try {
            String title = path.substring(path.lastIndexOf("/") + 1);
            if (title.contains(".")) title = title.substring(0, title.lastIndexOf("."));
            String setPath = path.substring(0, path.lastIndexOf("/"));
            Geodata geodata = geodataMapper.getByPath(setPath).get(0);
            GeodataItem geodataItem = geodataItemMapper.getItemBySetAndTitle(geodata.getId(), title).get(0);
            JSONObject remark = new JSONObject(geodataItem.getRemark());
            JSONObject tile = remark.optJSONObject("tile");
            JSONArray extents = remark.optJSONArray("extent");
            if (extents.length() == 4) {
                vizData.setBbox(new Double[]{
                        Math.max(-180.0, extents.getDouble(0)),
                        Math.max(-90.0, extents.getDouble(2)),
                        Math.min(180.0, extents.getDouble(1)),
                        Math.min(90.0, extents.getDouble(3))});
            }
            if (tile != null) {
                String type = tile.getString("type");
                String link;
                switch (type) {
                    case "pg":
                        vizData.setTileType("vector");
                        String tableName = tile.optString("tableName", "");
                        String layerName = tile.optString("layerName", "");
                        vizData.setLayerName(layerName);
                        String geomName = tile.optString("geomName", "geom");
                        link = "/tile/" + type + "/" + tableName + "/" + layerName + "/" + geomName + "/{z}/{x}/{y}";
                        vizData.setLink(link);
                        vizData.setGeomType(GeometryUtil.getGeomTypeByWkt(tileMapper.getOneWkt(tableName, geomName)));
                        break;
                    case "cog":
                        vizData.setTileType("raster");
                        link = tile.optString("link", "");
                        vizData.setLink(link);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vizData;
    }
}
