package com.ntnt.dut.crawlerapi.services;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.ntnt.dut.crawlerapi.enums.NotiType;
import com.ntnt.dut.crawlerapi.models.entities.NotificationEntity;
import com.ntnt.dut.crawlerapi.models.entities.ScheduleEntity;
import com.ntnt.dut.crawlerapi.models.entities.ScoreEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HtmlService {

    @Value("${web.crawler.home.url}")
    private String homeUrl;
    @Value("${web.crawler.login.url}")
    private String loginUrl;
    @Value("${web.crawler.notification.general.url}")
    private String notiGeneralUrl;
    @Value("${web.crawler.notification.class.url}")
    private String notiClassUrl;
    @Value("${web.crawler.schedule.url}")
    private String scheduleUrl;
    @Value("${web.crawler.score.url}")
    private String scoreUrl;
    @Value("${web.crawler.notification.param.page}")
    private String pageParam;
    @Value("${web.crawler.cookie.session.name}")
    private String sessionName;
    private WebClient webClient;

    public List<NotificationEntity> getNotifications(
            NotiType type,
            int page) {
        try {
            URL targetUrl = null;
            switch (type) {
                case GENERAL:
                    targetUrl = new URL(notiGeneralUrl + String.format("&%s=%d", pageParam, page));
                    break;
                case CLASS:
                    targetUrl = new URL(notiClassUrl + String.format("&%s=%d", pageParam, page));
                    break;
                default:
                    return null;
            }

            Document document = Jsoup.parse(targetUrl, 3000);
            Elements elements = document.getElementsByClass("tbBox");
            List<NotificationEntity> notifications = new ArrayList<>();
            elements.forEach(el -> {
                NotificationEntity notification = new NotificationEntity();
                Element caption = el.getElementsByClass("tbBoxCaption").get(0);

                String date = caption.select("b > span")
                        .first()
                        .text();
                date.substring(0, date.length() - 1);

                String title = caption.select("span")
                        .last()
                        .text();
                String content = el.getElementsByClass("tbBoxContent")
                        .first()
                        .html();

                notification.setType(NotiType.GENERAL);
                notification.setTitle(title);
                notification.setContent(content);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                try {
                    notification.setDate(format.parse(date));
                    notifications.add(notification);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            return notifications;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // datas is an array in which first element is the semester and the second is Cookie
    public ArrayList getDatasAfterLogin(String username, String password) {
        ArrayList datas = new ArrayList();
        try {
            webClient = new WebClient();
            webClient.getOptions().setCssEnabled(false);
            webClient.setJavaScriptTimeout(4000);

            WebRequest webRequest = new WebRequest(new URL(loginUrl), HttpMethod.GET);
            HtmlPage page = webClient.getPage(webRequest);
            HtmlForm loginForm = (HtmlForm) page.getElementById("ctl00");
            loginForm.getInputByName("ctl00$MainContent$DN_txtAcc").setValueAttribute(username);
            loginForm.getInputByName("ctl00$MainContent$DN_txtPass").setValueAttribute(password);
            HtmlPage userHomePage = loginForm.getInputByName("ctl00$MainContent$QLTH_btnLogin").click();

            // get class from the input tag
            HtmlInput inputClass = (HtmlInput) userHomePage.getElementById("CN_txtLop");
            datas.add(inputClass.getValueAttribute());

            Set<Cookie> cookies = webClient.getCookies(new URL(loginUrl));
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(sessionName)) {
                    datas.add(cookie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datas;
    }

    public List<ScheduleEntity> getSchedules(String cookie, String type) {
        List<ScheduleEntity> schedules = new ArrayList<>();
        String[] seperateCookie = cookie.split("=");
        try {
            Document document = Jsoup.connect(scheduleUrl + type)
                                        .cookie(seperateCookie[0], seperateCookie[1])
                                        .ignoreHttpErrors(true)
                                        .get();

            Element table = document.getElementById("TTKB_GridInfo");
            Elements rows = table.getElementsByClass("GridRow");
            rows.remove(rows.last());

            for (Element row : rows) {
                Elements cells = row.children();

                String dataCell3 = cells.get(3).text().replace(" ", "");
                String dataCell4 = cells.get(4).text().replace(" ", "")
                                                        .replace(",","");
                ScheduleEntity schedule = new ScheduleEntity(
                        0,
                        cells.get(1).text(),
                        cells.get(2).text(),
                        Float.parseFloat((dataCell3.isEmpty()) ? "-1" : dataCell3),
                        Float.parseFloat((dataCell4.isEmpty()) ? "-1" : dataCell4),
                        cells.get(5).text().trim().length() <= 0,
                        cells.get(6).text(),
                        cells.get(7).text(),
                        cells.get(8).text()
                );
                schedules.add(schedule);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schedules;
    }

    public List<ScoreEntity> getScores(String cookie) {
        //TODO: handle html
        List<ScoreEntity> scores = new ArrayList<>();
        String[] seperateCookie = cookie.split("=");
        try {
            Document document = Jsoup.connect(scoreUrl)
                                        .cookie(seperateCookie[0], seperateCookie[1])
                                        .ignoreHttpErrors(true)
                                        .get();

            Element table = document.getElementById("KQRLGridKQHT");
            Elements rows = table.getElementsByClass("GridRow");
            rows.remove(rows.last());

            for (Element row : rows) {
                Elements cells = row.children();

                String credit = cells.get(5).text().trim();
                String score1 = cells.get(7).text().trim();
                String score2 = cells.get(8).text().trim();
                String score3 = cells.get(9).text().trim();
                String score4 = cells.get(10).text().trim();
                String score5 = cells.get(11).text().trim();
                String score6 = cells.get(12).text().trim();
                String score7 = cells.get(13).text().trim();
                String score8 = cells.get(14).text().trim();
                ScoreEntity score = new ScoreEntity(
                        0,
                        cells.get(1).text().trim(),
                        cells.get(3).text().trim(),
                        cells.get(4).text().trim(),
                        Float.parseFloat((credit.isEmpty()) ? "-1" : credit),
                        Float.parseFloat((score1.isEmpty()) ? "-1" : score1),
                        Float.parseFloat((score2.isEmpty()) ? "-1" : score2),
                        Float.parseFloat((score3.isEmpty()) ? "-1" : score3),
                        Float.parseFloat((score4.isEmpty()) ? "-1" : score4),
                        Float.parseFloat((score5.isEmpty()) ? "-1" : score5),
                        Float.parseFloat((score6.isEmpty()) ? "-1" : score6),
                        Float.parseFloat((score7.isEmpty()) ? "-1" : score7),
                        Float.parseFloat((score8.isEmpty()) ? "-1" : score8),
                        cells.get(15).text().trim()
                );
                scores.add(score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return scores;
    }
}
