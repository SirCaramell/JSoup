package JSoup;

import java.io.IOException;
import java.sql.SQLOutput;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.*;

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

        Elements numberOfTR = cardSearchDoc.select("#innerNavBarCodeDiv > table > tbody > tr > td:nth-child(5) > a");

        int cardQuantity = numberOfTR.size();

        Elements[] allTR = new Elements[cardQuantity];
        String[] relHref = new String[cardQuantity];
        Connection[] cardSite = new Connection[cardQuantity];
        Document[] cardSiteDoc = new Document[cardQuantity];
        Elements[] allDD = new Elements[cardQuantity];
        Elements[] expansion = new Elements[cardQuantity];
        String[] priceTrend = new String[cardQuantity];

        for(int i=0;i<numberOfTR.size();i++)
        {
            int j = 1;
            j += i;

            allTR[i] = cardSearchDoc.select("#innerNavBarCodeDiv > table > tbody > tr:nth-child(" + j + ") > td:nth-child(5) > a");

            relHref[i] = allTR[i].attr("href");

            cardSite[i] = Jsoup.connect("https://www.cardmarket.com" + relHref[i]);

            cardSiteDoc[i] = cardSite[i].get();

            allDD[i] = cardSiteDoc[i].select("dd");

            expansion[i] = cardSiteDoc[i].select("#tabContent-info > div > div.col-12.col-lg-6.mx-auto > div > div.info-list-container.col-12.col-md-8.col-lg-12.mx-auto.align-self-start > dl > dd > div > a.mb-2");

            priceTrend[i] = allDD[i].last().text();

            System.out.println(allTR[i].first().text() + ", " + expansion[i].text() + " - " + "Price Trend: " + priceTrend[i].substring(0, priceTrend[i].indexOf("€") + 1));
        }
    }
}
