package com.google.firebase.codelab.friendlychat;

import android.app.ListActivity;
import android.provider.DocumentsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.firebase.codelab.friendlychat.MenuActivity.age;

public class Board10sActivity extends AppCompatActivity {

    //cardview 레이아웃
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    public ArrayList<Item> ItemArrayList = new ArrayList<>(); //기사 content

    String clientId = "qK6ocISgzi6FedNc8imk";//애플리케이션 클라이언트 아이디값";
    String clientSecret = "kEPlQcl4eW";//애플리케이션 클라이언트 시크릿값";


    TextView textView;


    //키워드를 담을 string타입 변수
    String keyword;
    //xml파일에 들어있는 성분의 존재유무값을 저장할 boolean변수들

    boolean inItem = false, inTitle = false, inDescription = false, inDate = false, inLink = false;
    //기사의 제목, 내용, 작성날짜, 링크 값을 저장할  stirng타입 변수들

    String title=null, description=null, date=null, link=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recycler_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        //뉴스 content가지고오기 -> 네이버 검색 api 사용
        getNews();


        while(ItemArrayList.size()<10){
            System.out.println("*지금" + ItemArrayList.size());
        }

        //카드뷰 레이아웃  ItemArrayList의 뉴스 갯수가 5개가 되면 뉴스들을 보여줌.
        My10Adapter mAdapter = new My10Adapter(ItemArrayList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void getNews(){




        new Thread() {
            @Override
            public void run() {
                try {

                //주소에 있는 링크에 있는 태그 속 내용 가져옴. 그중에서 검색어인 텍스트만 가져옴
                Document document = Jsoup.connect("https://datalab.naver.com/keyword/realtimeList.naver").get();
                Elements items = document.select("span.title");  //items 에는 전체연령대,10대,20대,30대,40대,50대이상의 실시간 급상승검색어
                                                                             //1위에서 20위까지가 순차적으로 들어있음(태그포함)
                ArrayList<String> keyword10 = new ArrayList<String>();      //각 연령대별 실시간 급상승검색어 1위~20위를 저장할 ArrayList

                int i;
                for(i = age*2; i<age*2+20; i++ ){
                    keyword10.add(items.get(i).text());               //index 0~19는 전체연령대의 실시간급상승 검색어 1위~20위
                                                                      //index 20~39는 10대의 실시간급상승 검색어 1위~20위 ArrayList에 순차적으로 추가.
                }



                    int[] ran_num = new int[5]; //검색순위에서 랜덤하게 뽑은 다섯개의 단어 인덱스가 저장 될 공간.

                    //5개의 랜덤 숫자를 서로 겹치지 않게 뽑아준다.
                    for(int m=0; m<5; m++){
                        ran_num[m] = (int)(Math.random()*20);

                        for(int n=0; n<m; n++){
                            if(ran_num[m]==ran_num[n]){
                                m--;
                            }
                        }
                    }

                    for(int wordCount=0; wordCount<5; wordCount++){
                        keyword = keyword10.get(ran_num[wordCount]); //인덱스배열에서 순차적으로 값을 불러와서 해당 인덱스에 키워드값을 keyword에 저장해준다.

                        //해당 키워드를 검색해주는 url 작성.
                        String apiURL = "https://openapi.naver.com/v1/search/news.xml?query=" + keyword + "&display=2&start=1&sort=sim"; //한 키워드당 두개의 기사를 불러온다.
                        URL url = new URL(apiURL);                        //string으로 된 url값을 URL타입으로 바꿔서 만들어 준다.
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();   //해당 url로 http 커넥션을 생성한다.
                        con.setRequestMethod("GET");   //기사들을 가져오기 위해 GET사용.
                        con.setRequestProperty("X-Naver-Client-Id", clientId);     //클라이언트 아이디값을 입력.
                        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);   //클라이언트 시크릿값을 입력.


                        int responseCode = con.getResponseCode();

                        if (responseCode == 200) {   //responseCode의 값이 200이면 연결 성공.

                            //xml형식으로 받은 파일 내용들을 성질에 따라 나눠주기 위한 작업.
                            //XmlPullParserFactory를 이용해서 parsing작업을 해줌.
                            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = parserCreator.newPullParser();

                            parser.setInput(con.getInputStream(), null);   //parser의 입력값을 커넥션을 통해 받아온 파일로 지정해줌.

                            int parserEvent = parser.getEventType();   //parser가 어느 부분에 있는지를 저장해주는 parserEvent변수 생성.

                            //xml문서의 끝까지 돌아가는 while문.
                            while (parserEvent != XmlPullParser.END_DOCUMENT) {

                                switch (parserEvent) {
                                    case XmlPullParser.START_TAG:   //태그 값에 따라 각각의 존재유무 boolean값을 지정해준다.

                                        if (parser.getName().equals("item")) {
                                            inItem = true;
                                        }
                                        if (parser.getName().equals("title")) {
                                            inTitle = true;
                                        }
                                        if (parser.getName().equals("description")) {
                                            inDescription = true;
                                        }
                                        if (parser.getName().equals("pubDate")) {
                                            inDate = true;
                                        }
                                        if (parser.getName().equals("link")) {
                                            inLink = true;
                                        }
                                        break;

                                    case XmlPullParser.TEXT:          //값이 있는 것들은 string변수로 그 값들을 복사해준다.
                                        if (inTitle) {
                                            title = parser.getText();
                                            inTitle = false;
                                        }
                                        if (inDescription) {
                                            description = parser.getText();
                                            inDescription = false;
                                        }
                                        if (inDate) {
                                            date = parser.getText();
                                            inDate = false;
                                        }
                                        if (inLink) {
                                            link = parser.getText();
                                            inLink = false;
                                        }
                                        break;

                                    case XmlPullParser.END_TAG:                       //문서 읽기 작업의 끝에 가면 원하는 태그 부분을 출력해줌.
                                        // 기사의 제목, 내용, 날짜, 링크 값을 선택하였다.
                                        if (parser.getName().equals("item")) {
                                            //쓸모없는 단어 제거
                                            String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";

                                            title = title.replace("br", "");
                                            title = title.replace("b", "");
                                            title = title.replace("quot", "");
                                            title = title.replace("lt", "");
                                            title = title.replace("gt", "");
                                            title = title.replaceAll(match, "");

                                            description = description.replace("br", "");
                                            description = description.replace("b", "");
                                            description = description.replace("quot", "");
                                            description = description.replace("lt", "");
                                            description = description.replace("gt", "");
                                            description = description.replaceAll(match, "");

                                            ItemArrayList.add(new Item(title, description, date, link));
                                        }
                                        break;
                                }
                                parserEvent = parser.next();
                            }

                        }
                    }
                } catch (Exception e) {
                    System.out.println(e + "   @@@@@@@@@@@@@@@@@#################################");
                }

            }
        }.start();



    }}