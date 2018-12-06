package JSoup;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        String masterString = new String();
        for(int i=0;i<args.length;i++)
        {
            if(i == args.length-1)
                masterString += args[i];
            else
                masterString += args[i] + "+";
        }

        Connection cardSearch = Jsoup.connect("https://www.cardmarket.com/en/Magic/MainPage/showSearchResult?searchFor=" + masterString);
        Document cardSearchDoc = cardSearch.get();


        Elements allTR = cardSearchDoc.select("#innerNavBarCodeDiv > table > tbody > tr > td:nth-child(5) > a");

        String relHref = allTR.attr("href");

        Connection cardSite = Jsoup.connect("https://www.cardmarket.com" + relHref);
        Document cardSiteDoc = cardSite.get();
        Elements allDD = cardSiteDoc.select("dd");
        Elements expansion = cardSiteDoc.select("#tabContent-info > div > div.col-12.col-lg-6.mx-auto > div > div.info-list-container.col-12.col-md-8.col-lg-12.mx-auto.align-self-start > dl > dd:nth-child(6) > div > a.mb-2");

        String priceTrend = allDD.last().text();
        System.out.println(allTR.first().text() + ", " + expansion.text() + " - " + "Price Trend: " + priceTrend.substring(0, priceTrend.indexOf("€")+1));

    }
}
