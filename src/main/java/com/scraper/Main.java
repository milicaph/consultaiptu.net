package com.scraper;

import excel.ReadWrite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ArrayList<String> namesOf = new ArrayList<String>();
        ArrayList<String> addressOf1 = new ArrayList<String>();
        ArrayList<String> addressOf2 = new ArrayList<String>();
        String urlFirstPart = Utils.getProperty("website"), urlInputpart1, urlInputPart2;
        ArrayList<String> urls = new ArrayList<String>();

        ReadWrite.readExcel(Utils.getProperty("input"));
        System.out.println(Utils.getProperty("input"));

        for (Map.Entry me : ReadWrite.hmap.entrySet()) {
            urlInputpart1 = me.getKey()
                    .toString()
                    .replaceAll("\\s", "-") + "/";
            urlInputPart2 = me.getValue()
                    .toString() + "/p/";
            urls.add(urlFirstPart + urlInputpart1 + urlInputPart2);
            System.out.println(urlFirstPart + urlInputpart1 + urlInputPart2);
        }

        urlsLoop:
        for (int i = 0; i < urls.size(); i++) {

            pagesLoop:
            for (int pageNum = 1; pageNum < 150; pageNum++) {
                String urlToConnect = urls.get(i) + pageNum;
                System.out.println(urlToConnect);
                try {
                    Document document = Jsoup.connect(urlToConnect)
                            .userAgent("Mozilla")
                            .timeout(0)
                            .get();

                    Elements divs = document.select("div[class*=collection-item]");
                    System.out.println(divs.size());

                    if (divs.size() == 0)
                        break pagesLoop;

                    divLoop:
                    for (Element div : divs) {

                            Elements names = div.child(1).getElementsByTag("a");
                            Element address = div.child(2);
                            String address1 = Utils.getOneString(address.html(), 0)
                                                   .trim();
                            System.out.println(address1);
                            String address2 = Utils.getOneString(address.html(), 1)
                                                   .trim();
                            if(address2.contains("<"))
                                address2 = Utils.removeHTML(address2);
                            System.out.println(address2);

                            nameLoop:
                            for(Element name : names){
                            namesOf.add(name.ownText());
                            addressOf1.add(address1);
                            addressOf2.add(address2);

                            }


                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
            System.out.println(addressOf1);
            ReadWrite.writeToExcel(Utils.getProperty("output"), namesOf, addressOf1, addressOf2);

    }
}
