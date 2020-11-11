package edu.zju.gis.dldsj.server.controller;

import edu.zju.gis.dldsj.server.base.BaseController;
import edu.zju.gis.dldsj.server.base.BaseFilter;
import edu.zju.gis.dldsj.server.common.Result;
import edu.zju.gis.dldsj.server.constant.CrawlerConstants;
import edu.zju.gis.dldsj.server.constant.TimeConstants;
import edu.zju.gis.dldsj.server.entity.Batch;
import edu.zju.gis.dldsj.server.entity.Lecture;
import edu.zju.gis.dldsj.server.service.LectureService;
import edu.zju.gis.dldsj.server.utils.CrawlerUtil;
import lombok.extern.log4j.Log4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zyq
 * @date 2020/10/23
 */

@Log4j
@Controller
@CrossOrigin
@RequestMapping("/lecture")
public class LectureController extends BaseController<Lecture, LectureService, String, BaseFilter<String>> {

    @Autowired
    private LectureService lectureService;

    /***
     * 爬取论文
     * @return result
     */
    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    @ResponseBody
    public Result<List<Batch<Lecture>>> crawl() {
        // 删除过期讲座
        Date now = new Date();
        lectureService.deleteBeforeTime(new Date(now.getTime() - TimeConstants.LectureReserveTime));
        // 爬取讲座
        String[] htmls = CrawlerUtil.crawl(CrawlerConstants.LectureUrls, CrawlerConstants.UserAgent);
        // 格式化讲座
        SimpleDateFormat sdf = new SimpleDateFormat("dd yyyy-MM");
        List<Lecture> lectures = new ArrayList<>();
        for(String html: htmls){
            Document document = Jsoup.parse(html);
            Elements elements = document.getElementsByClass("list-acad");
            for(Element element: elements){
                try{
                    Lecture lecture = new Lecture();
                    lecture.setName(element.getElementsByTag("a").text());
                    lecture.setTime(sdf.parse(element.getElementsByClass("acad-time").text()));
                    lecture.setSpeaker(element.getElementsByClass("acad-con-speaker").text()
                            .replace("主讲人", "")
                            .replace("：", "")
                            .replace(":", "").trim());
                    lecture.setPlace(element.getElementsByClass("acad-con-place").text()
                            .replace("地点", "")
                            .replace("：", "")
                            .replace(":", "").trim());
                    lecture.setDetailTime(element.getElementsByClass("acad-con-time").text()
                            .replace("时间", "")
                            .replace("：", "")
                            .replace(":", "").trim());
                    lecture.setUrl("http://gs.zju.edu.cn/" + element.getElementsByTag("a").first().attr("href"));
                    lectures.add(lecture);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        // 筛选讲座
        List<Lecture> filter1 = new ArrayList<>();
        for(Lecture lecture: lectures){
            if(lecture.getTime().getTime() >= now.getTime() - TimeConstants.LectureReserveTime) filter1.add(lecture);
        }
        List<Lecture> filter2 = new ArrayList<>();
        for(Lecture lecture: filter1){
            if(lectureService.selectByName(lecture.getName()) == null) filter2.add(lecture);
        }

        return lectureService.batchInsert(filter2);
    }

}
