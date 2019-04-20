package com.example.schooldetect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context) {
        super(context, "SchoolApp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table User (Account TEXT NOT NULL, Password TEXT NOT NULL);");
        db.execSQL("Create Table Type (tid INTEGER NOT NULL, etype TEXT NOT NULL,ctype TEXT NOT NULL);");
        initdbtype(db);
        db.execSQL("Create Table Mark (sid INTEGER not null, Account Text NOT NULL, mark integer);");
        db.execSQL("Create Table School (sid Integer not null, tid INTEGER not null, cname TEXT not null, ename TEXT not null,X TEXT not null, Y TEXT not null, caddress TEXT not null, eaddress TEXT not null);");
        insertschooldata(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void initdbtype( SQLiteDatabase sqLiteDatabase) {
        ArrayList etype = new ArrayList();
        etype.add("Primary");
        etype.add("Secondary");
        etype.add("University");

        ArrayList ctype = new ArrayList();
        ctype.add("小學");
        ctype.add("中學");
        ctype.add("大學");

        for (int i = 0; i < etype.size(); i++) {
            ContentValues typev = new ContentValues();
            typev.put("tid", i+1);
            typev.put("etype", etype.get(i).toString());
            typev.put("ctype", ctype.get(i).toString());
            long result= sqLiteDatabase.insert("Type", null, typev);
            if (result > 0) {
                Log.d("dbhelper", " OKOK");
            } else {
                Log.d("dbhelper", "NOOOOOO");
            }
        }
    }



    private void insertschooldata(SQLiteDatabase sqLiteDatabase) {
        ArrayList school = new ArrayList();
        school.add("1,1,中華基督教會灣仔堂基道小學（九龍城）,CCC Wanchai Church Kei To Primary School (Kowloon City),\"22.3155818,114.183747\",香港九龍何文田常樂街,Hong Kong  九龍何文田常樂街");
        school.add("2,1,陳瑞祺（喇沙）小學,Chan Sui Ki (La Salle) Primary School,\"22.3188317,114.179089\",香港何文田文福道27號,27 Man Fuk Rd  Ho Man Tin  Hong Kong");
        school.add("3,1,基督教香港信義會紅磡信義學校,E.L.C.H.K. Hung Hom Lutheran Primary School,\"22.3184139,114.1857083\",香港採石山常盛街80號半山壹號一期半山徑31號,Celestial Heights Phase 1 31 Celestial Avenue  80 Sheung Shing St  Quarry Hill  Hong Kong");
        school.add("4,1,九龍靈光小學,Emmanuel Primary School - Kowloon,\"22.3240447,114.1857415\",香港老龍坑漆咸道北388號昇御門,Chatham Gate  388 Chatham Rd N  Lo Lung Hang  Hong Kong");
        school.add("5,1,農圃道官立小學,Farm Road Government Primary School,\"22.3208319,114.1844553\",香港採石山天光道16號,16 Tin Kwong Rd  Quarry Hill  Hong Kong");
        school.add("6,1,協恩中學附屬小學,Heep Yunn Primary School,\"22.3217399,114.1830923\",香港何文田亞皆老街204號,204 Argyle St  Ho Man Tin  Hong Kong");
        school.add("7,1,合一堂學校,Hop Yat Church School,\"22.320313,114.1770222\",香港何文田公主道80號,80 Princess Margaret Rd  Ho Man Tin  Hong Kong");
        school.add("8,1,九龍婦女福利會李炳紀念學校,Kowloon Women's Welfare Club Li Ping Memorial School,\"22.3129339,114.1733323\",香港何文田窩打老道86-86E號萬基大廈,Man Kee Mansion  86-86E Waterloo Rd  Ho Man Tin  Hong Kong");
        school.add("9,1,天主教領島學校,Ling To Catholic Primary School,\"22.3147549,114.1771173\",香港油麻地窩打老道54A號,54A Waterloo Rd  Yau Ma Tei  Hong Kong");
        school.add("10,1,馬頭涌官立小學,Ma Tau Chung Government Primary School,\"22.3110528,114.1863615\",Peel Block  Ho Man Tin  香港,Peel Block  Ho Man Tin  Hong Kong");
        school.add("12,1,聖公會牧愛小學,聖公會牧愛小學S.K.H. Good Shepherd Primary School,\"22.3181871,114.1857199\", 香港採石山常盛街55-57號常盛閣, Lok Man Sun Chuen Sites 1 And 2 Block G Lok Seen Lau  120-148 Kau Pui Lung Rd  To Kwa Wan  Hong Kong");
        school.add("13,1,聖公會聖匠小學,S.K.H. Holy Carpenter Primary School,\"22.3163635,114.19058\",2 香港土瓜灣靠背壟道120-148號樂民新邨一及二期G座樂善樓, Mei King Mansion Phase 1  21 Lok Shan Rd  To Kwa Wan  Hong Kong");
        school.add("14,1,聖公會聖十架小學,S.K.H. Holy Cross Primary School,\"22.3328806,114.2020549\", 香港土瓜灣落山道21號美景樓1期, Ching Long Shopping Centre Zone A  Kowloon City  Hong Kong");
        school.add("15,1,獻主會聖馬善樂小學,St. Eugene de Mazenod Oblate Primary School,\"22.3199633,114.1840463\",2 香港九龍灣啟業邨啟樂樓, Sheung Shing Court  55-57 Sheung Shing St  Quarry Hill  Hong Kong");
        school.add("16,1,聖羅撒學校,St. Rose of Lima's School,\"22.326241,114.172881\", 香港九龍城晴朗商場（A區）, 37 Flower Market Rd  Mong Kok  Hong Kong");
        school.add("17,1,黃埔宣道小學,Alliance Primary School  Whampoa,\"22.303128,114.188717\", 香港畢架山廣播道87號廣播道87號, Whampoa Garden Site 4 Palm Mansions Block 6  7 Shung King St  Whampoa Garden  Hong Kong");
        school.add("18,1,葛量洪校友會黃埔學校,GCEPSA Whampoa Primary School ,\"22.303541,114.1892083\", 香港採石山石鼓街22號, Whampoa Garden Site 4 Palm Mansions Block 2  7 Shung King St  Whampoa Garden  Hong Kong");
        school.add("19,1,天神嘉諾撒學校,Holy Angels Canossian School,\"22.3107305,114.1839077\", 香港旺角花墟道37號, Former Village Road Estate  No. 12 Hill  Hong Kong");
        school.add("20,1,馬頭涌官立小學（紅磡灣）,Ma Tau Chung Government Primary School (Hung Hom Bay),\"22.3023902,114.1857251\", 香港李鄭屋青山道220-240A號, Harbour Place  Hung Hom  Hong Kong");
        school.add("21,1,聖公會奉基千禧小學,S.K.H. Fung Kei Millennium Primary School,\"22.304392,114.188984\", 香港土瓜灣土瓜灣道241-247N號美景樓2期, 33 Inverness Rd  Kowloon Tsai  Hong Kong");
        school.add("22,1,聖公會奉基小學,S.K.H. Fung Kei Primary School,\"22.3047832,114.1893476\", 香港九龍塘港鐵九龍塘站KOT 124號舖, Hong Kong  九龍塘港鐵九龍塘站KOT 124號舖");
        school.add("23,1,聖公會聖提摩太小學,S.K.H. St. Timothy's Primary Schoo,\"22.310768,114.184654\", 香港雲景道56號富豪閣1座, 1-27 Wing Kwong Street  1-27 Wing Kwong St  To Kwa Wan  Hong Kong");
        school.add("24,1,華德學校,Bishop Walsh Primary School,\"22.3345755,114.184903\", Peel Block  Ho Man Tin  香港, Scholars' Lodge  1 Derby Rd  Kowloon Tsai  Hong Kong");
        school.add("25,1,中華基督教會基華小學（九龍塘）,C.C.C. Kei Wa Primary School (Kowloon Tong),\"22.3297195,114.1782941\", 香港黃埔花園2期地下G22號舖, 11 Shek Ku St  Quarry Hill  Hong Kong");
        school.add("26,1,拔萃小學,Diocesan Preparatory School,\"22.329166,114.176644\", 香港又一村花圃街12-14號花圃別墅, 9 Shung King St  Hung Hom  Hong Kong");
        school.add("27,1,嘉諾撒聖家學校,Holy Family Canossian School,\"22.3291657,114.1700511\", Inverness Road  香港, 7 Tong Yam St  Hong Kong");
        school.add("28,1,嘉諾撒聖家學校（九龍塘）,Holy Family Canossian School (Kowloon Tong),\"22.3364926,114.1763887\", 香港又一村丹桂路5號富澤苑, Hong Kong  Kowloon Tong  Cumberland Rd  25號佛教感恩堂");
        school.add("29,1,耀山學校,Iu Shan School,\"22.3300494,114.1846154\", 香港十二號山忠孝街66號俊民苑文凱閣, Beverley Heights Block 1  56 Cloud View Rd  Hong Kong");
        school.add("30,1,九龍塘天主教華德學校,Kowloon Tong Bishop Walsh Catholic School,\"22.334451,114.18422\", 香港九龍仔解放軍東九龍軍營, 33 Inverness Rd  Kowloon Tsai  Hong Kong");
        school.add("31,1,九龍塘官立小學,Kowloon Tong Government Primary School,\"22.3354138,114.1763371\", 香港慈雲山蒲英里1-9號華麗樓, Village Gardens Phase C 46 Fa Po St  46 Fa Po St  Yau Yat Chuen  Hong Kong");
        school.add("32,1,獻主會小學,Oblate Primary School,\"22.315958,114.1896\", 香港九龍塘雅息士道7號雅息士道7號, Hong Kong  黃埔花園2期地下G22號舖");
        school.add("33,1,保良局何壽南小學,P.L.K. Stanley Ho Sau Nan Primary School,\"22.3317508,114.2042186\", 香港何文田巴富街5號巴富花園怡富閣, Wah Lai House  1-9 Po Ying Ln  Tsz Wan Shan  Hong Kong");
        school.add("34,1,聖公會牧愛小學,S.K.H. Good Shepherd Primary School,\"22.3181871,114.185719\", 7.5 miles  Tai Po Road  Sha Tin  Tai Wai  香港, 83 Lion Rock Road  83 Lion Rock Rd  Kowloon City  Hong Kong");
        school.add("35,1,聖公會聖匠小學,S.K.H. Holy Carpenter Primary School,\"22.3163635,114.1905\", 香港大帽山麥理浩徑8段, 30 Chui Ling Rd  Tiu Keng Wan  Hong Kong");
        school.add("36,1,聖公會聖十架小學,S.K.H. Holy Cross Primary School,\"22.3328806,114.202054\", 香港土瓜灣土瓜灣道241-247N號美景樓2期, Gun Club Hill Barracks  127 Austin Rd  Yau Ma Tei  Hong Kong");
        school.add("37,1,獻主會聖馬善樂小學,St. Eugene de Mazenod Oblate Primary School,\"22.3199633,114.184046\", Inverness Road  香港, Sheung Shing Court  55-57 Sheung Shing St  Quarry Hill  Hong Kong");
        school.add("38,1,聖羅撒學校,St. Rose of Lima's School,\"22.326241,114.17288\", 香港旺角染布房街8號, Peel Block  Ho Man Tin  Hong Kong");
        school.add("39,1,樂善堂小學,Lok Sin Tong Primary School,\"22.33041,114.18806\", 香港九龍仔延文禮士道33號, Lok Man Sun Chuen Sites 1 And 2 Block G Lok Seen Lau  120-148 Kau Pui Lung Rd  To Kwa Wan  Hong Kong");
        school.add("40,1,喇沙小學,La Salle Primary School,\"22.327398,114.1791441\", 香港加多利山九龍醫院, Tin Hau Temple Road Garden No.3  70 Tin Hau Temple Rd  Hong Kong");
        school.add("41,1,九龍塘宣道小學,Maryknoll Convent School (Primary Section) Alliance Primary School  Kowloon Tong,\"22.330174,114.177136\", 香港土瓜灣落山道21號美景樓1期, Parkland Villas Phase 1 Block 4  1 Tuen On Ln  Tuen Mun  Hong Kong");
        school.add("42,1,瑪利諾修院學校（小學部）,Alliance Primary School  Kowloon Tong,\"22.3278235,114.177167\", 香港馬鞍山雅典居, Mei King Mansion Phase 2  241-247N To Kwa Wan Rd  To Kwa Wan  Hong Kong");
        school.add("43,1,九龍塘學校（小學部）,Kowloon Tong School (Primary Section),\"22.3275047,114.174188\", 香港九龍塘金巴倫道25號佛教感恩堂, Lung Cheung Building  68 Waterloo Rd  Ho Man Tin  Hong Kong");
        school.add("44,1,九龍真光中學（小學部）,Kowloon True Light School (Primary Section),\"22.330236,114.17618\", 香港馬頭圍亞皆老街212-216號恆時大樓, Sceneway Garden Podium  Hong Kong");
        school.add("45,1,民生書院小學,Munsang College Primary School,\"22.332712,114.18393\", 香港九龍仔延文禮士道7號九龍仔長者健體園地, 37 Flower Market Rd  Mong Kok  Hong Kong");
        school.add("46,1,聖三一堂小學,Holy Trinity Primary School,\"22.325128,114.18629\", 香港紅磡海濱南岸, Choi Hung Estate Kam Pik House  8 Luk Lau Ave  Ngau Chi Wan  Hong Kong");
        school.add("47,1,保良局林文燦英文小學,Po Leung Kuk Lam Man Chan English Primary School,\"22.3197934,114.184701\", 香港油麻地窩打老道9-15號榮德大廈, Parc Palais Block 6  18 Wylie Rd  Yau Ma Tei  Hong Kong");
        school.add("48,1,香港培道小學,Pooi To Primary School,\"22.3244545,114.18403\", 香港九龍城獅子石道83號獅子石道83號, Hang Cheung Factory Building  1 Wing Ming St  Cheung Sha Wan  Hong Kong");
        school.add("49,1,香港培正小學,Pui Ching Primary School,\"22.318218,114.173332\", 香港何文田窩打老道68號龍翔大廈, Yau Luen Apartments  70A-70D Tai Po Rd  Tong Mi  Hong Kong");
        school.add("50,1,聖若望英文書院（小學部）,St. Johannes College (Primary Section),\"22.333811,114.176392\", 香港上元嶺志蓮淨苑, Kowloon Tsai Park Fitness Corner for the Elderly  7 Inverness Rd  Kowloon Tsai  Hong Kong");
        school.add("51,2,何明華會督銀禧中學,Bishop Hall Jubilee School,\"22.329805,114.1787\", 香港何文田文運道9號, 13 Inverness Rd  Kowloon Tsai  Hong Kong");
        school.add("52,2,迦密中學,Carmel Secondary School,\"22.310125,114.177723\", 香港何文田公主道6號, 7.5 miles  Tai Po Road  Sha Tin  Tai Wai  Hong Kong");
        school.add("53,2,中華基督教會基道中學,CCC Kei To Secondary School,\"22.3134525,114.188279\", 香港何文田文福道27號, 40 Fung Mo Street  Chuk Un  Hong Kong");
        school.add("54,2,陳瑞祺（喇沙）書院,Chan Sui Ki (La Salle) College,\"22.3194195,114.181405\", 香港馬料水池旁路香港中文大學牟路思怡圖書館, 7 Essex Road  7 Essex Cres  Kowloon Tong  Hong Kong");
        school.add("55,2,拔萃男書院,Diocesan Boys' School,\"22.3223899,114.17174\", 香港馬頭圍亞皆老街206號, Wang Tau Hom Estate Wang Yiu House  17 Fu Mei St  Wang Tau Hom  Hong Kong");
        school.add("56,2,協恩中學,Heep Yunn School,\"22.321289,114.1834\", 香港九龍仔聯福道3號亞洲婦女協會何梁潔庭頤養之家護理安老院, 5 Lincoln Road  5 Lincoln Rd  Kowloon Tong  Hong Kong");
        school.add("57,2,香港兆基創意書院 (李兆基基金會贊助、香港當代文化中心主辦),HKICC Lee Shau Kee School of Creativity,\"22.3335328,114.1848573\", 香港九龍仔志士達道1-3號拔萃小學, 22 Shek Ku St  Quarry Hill  Hong Kong");
        school.add("58,2,旅港開平商會中學,Hoi Ping Chamber of Commerce Secondary School,\"22.319303,114.1785\", 香港長沙灣元州街303號元州邨元康樓, 11號 Muk Hung St  Kowloon Bay  Hong Kong");
        school.add("59,2,嘉諾撒聖家書院,Holy Family Canossian College,\"22.334149,114.18323\", 香港紅磡蕪湖街129-131號蕪湖街129-131號, Kowloon Hospital  Kadoorie Hill  Hong Kong");
        school.add("60,2,何文田官立中學,Homantin Government Secondary School,\"22.3210296,114.178108\", 香港九龍塘達之路72號創新中心, Harvest Court  212-216 Argyle St  Ma Tau Wai  Hong Kong");
        school.add("61,2,賽馬會官立中學,Jockey Club Government Secondary School,\"22.3293553,114.17894\", 香港九龍仔窩打老道130號, 186 Nga Tsin Wai Rd  Kowloon Tsai  Hong Kong");
        school.add("62,2,九龍塘學校（中學部）,Kowloon Tong School (Secondary Section),\"22.328704,114.17365\", 香港太子界限街101號花墟公園, 30 Hereford Rd  Kowloon Tsai  Hong Kong");
        school.add("63,2,九龍真光中學,Kowloon True Light School,\"22.3287037,114.16706\", 香港柴灣怡順街3號柴灣公園, 6 Princess Margaret Rd  Ho Man Tin  Hong Kong");
        school.add("64,2,喇沙書院,La Salle College,\"22.3287064,114.1805699\", 香港採石山天光道16號, 18 Wylie Rd  Yau Ma Tei  Hong Kong");
        school.add("65,2,瑪利諾修院學校（中學部）,Maryknoll Convent School (Secondary Section),\"22.328216,114.1772\", 香港又一村壽菊路12號, 5 Ho Tung Rd  Kowloon Tsai  Hong Kong");
        school.add("66,2,民生書院,Munsang College,\"22.3324131,114.18397\", 香港油麻地衛理道18號, Inverness Road  Hong Kong");
        school.add("67,2,新亞中學,New Asia Middle School,\"22.3205083,114.183995\", 香港採石山常盛街51號香港耀能協會盛康園,2 Hong Kong  Kowloon Bay  啟業邨啟樂樓");
        school.add("68,2,獻主會聖母院書院,Notre Dame College,\"22.324522,114.185546\", 香港採石山常盛街55-57號常盛閣, Inverness Road  Hong Kong");
        school.add("69,2,五旬節中學,Pentecostal School,\"22.3212617,114.179075\", 香港又一村花圃街46號又一村花園三期花圃街46號, Fa Po Villa  12-14 Fa Po St  Yau Yat Chuen  Hong Kong");
        school.add("70,2,保良局顏寶鈴書院,Po Leung Kuk Ngan Po Ling College,\"22.3128861,114.18800\", 香港土瓜灣榮光街1-27號榮光街1-27號, Fa Hui Park  101號 Boundary St  Prince Edward  Hong Kong");
        school.add("71,2,香港培道中學,Pooi To Middle School,\"22.3300888,114.183528\", 香港赤柱赤柱市場道20號, Lederle Garden  4 Shun Yung St  Lo Lung Hang  Hong Kong");
        school.add("72,2,香港培正中學,Pui Ching Middle School,\"22.317343,114.1746\", 香港旺角弼街56號, Mong Kok East Station  Kadoorie Hill  Hong Kong");
        school.add("73,2,禮賢會彭學高紀念中學,Rhenish Church Pang Hok Ko Memorial College,\"22.3344077,114.18094\", 香港南山邨道28號, 113A Waterloo Rd  Kowloon Tong  Hong Kong");
        school.add("74,2,創知中學,Scientia Secondary School,\"22.319971,114.17664\", 香港九龍灣沐虹街11號, The Chinese University of Hong Kong Elizabeth Luce Moore Library  Pond Cres  Ma Liu Shui  Hong Kong");
        school.add("75,2,聖公會聖匠中學,SKH Holy Carpenter Secondary School,\"22.3075856,114.185\", 香港九龍塘林肯道5號林肯道5號, Ching Long Shopping Centre Zone A  Kowloon City  Hong Kong");
        school.add("76,2,聖公會聖三一堂中學,S.K.H. Holy Trinity Church Secondary School,\"22.3107574,114.177832\", 香港九龍仔延文禮士道13號, People's Liberation Army Kowloon East Barracks  Kowloon Tsai  Hong Kong");
        school.add("77,2,聖公會蔡功譜中學,SKH Tsoi Kung Po Secondary School,\"22.31495,114.178412\", 香港土瓜灣環發街1-19號環發街1-19號, Maclehose Trail Sec. 8  Tai Mo Shan  Hong Kong");
        school.add("78,2,德蘭中學,St. Teresa Secondary School,\"22.3188047,114.17829\", 香港加多利山旺角東站, 220-240A Castle Peak Rd  Lei Cheng Uk  Hong Kong");
        school.add("79,2,順德聯誼總會胡兆熾中學,STFA Seaward Woo College,\"22.3227446,114.18029\", 40 Fung Mo Street  Chuk Un  香港, Man Kee Mansion  Ho Man Tin  Hong Kong");
        school.add("80,2,鄧鏡波學校,Tang King Po School,\"22.32026,114.182653\", 香港採石山石鼓街11號, SAHK LOHAS Garden  51 Sheung Shing St  Quarry Hill  Hong Kong");
        school.add("81,2,基督教女青年會丘佐榮中學,The Y.W.C.A. Hioe Tjo Yoeng College,\"22.3198269,114.181034\", 香港紅磡船景街9號, 56 Bute St  Mong Kok  Hong Kong");
        school.add("82,2,東華三院黃笏南中學,TWGHs Wong Fut Nam College,\"22.329156,114.178313\", 香港何文田培正道5號九龍公共圖書館, 12 Marigold Rd  Yau Yat Chuen  Hong Kong");
        school.add("83,2,華英中學,Wa Ying College,\"22.319518,114.1805\", 香港棠蔭街7號, Chi Lin Nunnery  Sheung Yuen Leng  Hong Kong");
        school.add("84,2,余振強紀念中學,Yu Chun Keung Memorial College,\"22.318445,114.17640\", 香港油麻地柯士甸道127號槍會山軍營, 20 Stanley Market Rd  Stanley  Hong Kong");
        school.add("85,2,佛教孔仙洲紀念中學,Buddhist Hung Sean Chau Memorial College,\"22.3186276,114.17837\", 香港牛池灣綠柳路8號彩虹邨金碧樓, 1-19 Wan Fat Sreet  1-19 Wan Fat St  To Kwa Wan  Hong Kong");
        school.add("86,2,明愛馬鞍山中學,Caritas Ma On Shan Secondary School,\"22.4254188,114.236809\", 香港採石山石鼓街9號, Osmanthus Court  5 Osmanthus Rd  Yau Yat Chuen  Hong Kong");
        school.add("87,2,中華基督教會協和書院,CCC Heep Woh College,\"22.3467574,114.20235\", 香港九龍塘雅息士道5號雅息士道5號, Astoria  Ma Tau Wai  Hong Kong");
        school.add("88,2,中華基督教會基協中學,CCC Kei Heep Secondary School,\"22.338727,114.19136\", 香港九龍何文田媽橫路好運樓, Chun Man Court Man Hoi House  66 Chung Hau St  No. 12 Hill  Hong Kong");
        school.add("89,2,中華基督教會扶輪中學,CCC Rotary Secondary School,\"22.3419894,114.186525\", 香港九龍仔延文禮士道33號, 28 Nam Shan Chuen Rd  Hong Kong");
        school.add("90,2,陳樹渠紀念中學,Chan Shu Kui Memorial School,\"22.329263,114.17297\", 香港老龍坑信用街4號首利大廈, Wing Tak Building  9-15 Waterloo Rd  Yau Ma Tei  Hong Kong");
        school.add("91,2,佛教志蓮中學,Chi Lin Buddhist Secondary School,\"22.3403465,114.20430\", 香港照鏡環翠嶺路30號, 8 Yim Po Fong St  Mong Kok  Hong Kong");
        school.add("92,2,中聖書院,China Holiness College,\"22.33717,114.15831\", 香港匯景花園平台, 206 Argyle St  Ma Tau Wai  Hong Kong");
        school.add("93,2,彩虹邨天主教英文中學,Choi Hung Estate Catholic Secondary School,\"22.3355067,114.2051378\", 香港黃埔花園船景街7號黃埔花園4期棕櫚苑2座, New Method College  Ho Man Tin  Hong Kong");
        school.add("95,2,基督教香港信義會信義中學,ELCHK Lutheran Secondary School,\"22.3131125,114.170144\", 香港橫頭磡富美街17號橫頭磡邨宏耀樓, Hong Kong  九龍何文田媽橫路好運樓");
        school.add("96,2,五邑司徒浩中學,FDBWA Szeto Ho Secondary School,\"22.306361,114.232663\", 香港九龍仔打比道1號聚賢居, Mei King Mansion Phase 1  21 Lok Shan Rd  To Kwa Wan  Hong Kong");
        school.add("97,2,東莞工商總會劉百樂中學,GCC&ITKD Lau Pak Lok Secondary School,\"22.372677,114.173294\", 香港十二號山前山谷道邨, 5 Essex Crescent  5 Essex Cres  Kowloon Tong  Hong Kong   ");
        school.add("98,2,香島中學,Heung To Middle School,\"22.335386,114.171791\", 香港長沙灣永明街1號恆昌工廠大廈, 130 Waterloo Rd  Kowloon Tsai  Hong Kong");
        school.add("99,2,將軍澳香島中學,Heung To Secondary School (Tseung Kwan O),\"22.3048072,114.2473285\", 香港馬頭圍雅士花園, Villa Athena  Ma On Shan  Hong Kong");
        school.add("100,2,港九潮州公會中學,HK & KLN Chiu Chow Public Assn. Sec. School,\"22.3220001,114.169191\", 香港塘尾大埔道70A-70D號友聯大樓, Asia Women's League Ho Leung Kit Ting Care And Attention Home For The Elderly  3 Renfrew Rd  Kowloon Tsai  Hong Kong");
        school.add("101,2,香港布廠商會朱石麟中學,HKWMA Chu Shek Lun Secondary School,\"22.327418,114.209308\", 香港九龍仔禧福道30號, 16 Tin Kwong Rd  Quarry Hill  Hong Kong\n");
        school.add("102,2,可立中學（嗇色園主辦）,Ho Lap College (Sponsored by Sik Sik Yuen),\"22.327418,114.209308\", 香港九龍仔何東道5號, 129-131 Wuhu Street  129-131 Wuhu St  Hung Hom  Hong Kong");
        school.add("103,2,香港航海學校,Hong Kong Sea School,\"22.2184121,114.2119672\", 香港油麻地衛理道18號君頤峰6座, Kowloon Tong Club  Kowloon Tong  Hong Kong");
        school.add("104,2,伊斯蘭脫維善紀念中學,Islamic Kasim Tuet Memorial College,\"22.2667299,114.2378888\", 香港九龍塘窩打老道113A號, Perth Garden Ilford Court  5 Perth St  Ho Man Tin  Hong Kong");
        school.add("105,2,蘇浙公學,Kiangsu-Chekiang College,\"22.2870579,114.1982256\", 香港天后廟道70號天后廟道三號花園, Diocesan Preparatory School  1-3 Chester Rd  Kowloon Tsai  Hong Kong");
        school.add("106,2,潔心林炳炎中學,Kit Sam Lam Bing Yim Secondary School,\"22.341075,114.1832\", 香港何文田新法書院, Innocentre  72 Tat Chee Ave  Kowloon Tong  Hong Kong");
        school.add("107,3,香港大學,HKU,\"22.3410712,114.150278\", 香港十二號山忠孝街83號御龍居2座, 9 Man Wan Rd  Ho Man Tin  Hong Kong");
        school.add("108,3,中文大學,CU,\"22.4162632,114.2087378\", 香港九龍塘九龍塘會, 9 Shek Ku St  Quarry Hill  Hong Kong");
        school.add("109,3,香港科技大學,UST,\"22.416248,114.1407096\", 香港九龍仔衙前圍道186號, Kowloon Public Library  5 Pui Ching Rd  Ho Man Tin  Hong Kong");
        school.add("110,3,香港浸會大學,BU,\"22.3386388,114.1797376\", 香港旺角花墟道37號, 27 Man Fuk Rd  Ho Man Tin  Hong Kong");
        school.add("111,3,香港樹仁大學,SYU,\"22.286159,114.195557\", 香港九龍城晴朗商場（A區）, Chai Wan Park  3 Yee Shun St  Chai Wan  Hong Kong");
        school.add("112,3,香港理工大學,POLYU,\"22.3045719,114.1773702\", 香港何文田萬基大廈, Un Chau Estate Un Hong House  303 Un Chau St  Cheung Sha Wan  Hong Kong");
        school.add("113,3,香港城市大學,CITYU,\"22.3374855,114.15445\", 香港屯門屯安里1號叠茵庭一期4座, Dragon View Block 2  83 Chung Hau St  No. 12 Hill  Hong Kong");
        school.add("114,3,嶺南大學,LU,\"22.410339,113.98166\", 87 Broadcast Drive  87 Broadcast Dr  Beacon Hill  Hong Kong, 87 Broadcast Drive  87 Broadcast Dr  Beacon Hill  Hong Kong");


        for (int i = 0; i < school.size(); i++) {
            ContentValues schoola = new ContentValues();
            int id = Integer.parseInt(school.get(i).toString().split(",")[0]);
            int tid = Integer.parseInt(school.get(i).toString().split(",")[1]);
            String cname =school.get(i).toString().split(",")[2];
            String ename =school.get(i).toString().split(",")[3];
            String X =school.get(i).toString().split(",")[4].replace("\"","");
            String Y =school.get(i).toString().split(",")[5].replace("\"","");
            String caddress =school.get(i).toString().split(",")[6];
            String eaddress =school.get(i).toString().split(",")[7];


            schoola.put("sid",id);
            schoola.put("tid",tid);
            schoola.put("cname",cname);
            schoola.put("ename",ename);
            schoola.put("X",X);
            schoola.put("Y",Y);
            schoola.put("caddress",caddress);
            schoola.put("eaddress",eaddress);


            long result= sqLiteDatabase.insert("School", null, schoola);
            if (result > 0) {
                Log.d("dbhelper", "Register successfully");
            } else {
                Log.d("dbhelper", "failed to register");
            }
        }
    }

    public void addAccount(Account ac) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ContentValues acvalue = new ContentValues();
        acvalue.put("Account", ac.getac());
        acvalue.put("Password", ac.getpw());

        long result = sqLiteDatabase.insert("User", null, acvalue);

        if (result > 0) {
            Log.d("dbhelper", "Register successfully");
        } else {
            Log.d("dbhelper", "failed to register");
        }
        sqLiteDatabase.close();
    }

    public boolean checkAccount(Account ac) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String account = ac.getac();
        Cursor c = sqLiteDatabase.rawQuery("Select * From User where Account = '" + account + "'", null);
        boolean checker = false;
        if (c.getCount() == 0) {
            checker = true;
        }
        sqLiteDatabase.close();
        return checker;
    }

    public boolean checkLogin(Account ac) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String account = ac.getac();
        String password = ac.getpw();
        Cursor c = sqLiteDatabase.rawQuery("Select * From User where Account = '" + account + "' AND password = '" + password + "'", null);
        boolean checker = false;
        if (c.getCount() == 1) {
            checker = true;
        }
        sqLiteDatabase.close();
            return checker;
    }


    public ArrayList getType(String name) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * From Type Order by tid", null);
        ArrayList a = new ArrayList();
        if (c != null) {
            if (c.moveToFirst()) {
                    do {
                        if (name == "eng") {
                            a.add(c.getString(1));
                        } else {
                            a.add(c.getString(2));
                        }
                    } while (c.moveToNext());
            }
        }

        sqLiteDatabase.close();
        return a;
    }

    public ArrayList getSchool(String type) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * From Type Where etype = '"+type+"' OR ctype = '" + type + "'", null);
        int id = 1;
        ArrayList a = new ArrayList();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    id = c.getInt(0);
                } while (c.moveToNext());
            }
        }

        Cursor c4 = sqLiteDatabase.rawQuery("Select * From School Where tid =" + id , null);
        if (c4 != null) {
            if (c4.moveToFirst()) {
                do {
                   a.add(c4.getInt(0) + "@" + c4.getString(2) + "@" + c4.getString(3));
                } while (c4.moveToNext());
            }
        }
        sqLiteDatabase.close();
        return a;
    }

    public SchoolDetailData getSchoolbyId(String id) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * From School Where sid = "+id , null);

        String cname = "";
        String ename = "";
        String caddress = "";
        String daddress = "";
        double mark = 0;
        int counter = 0;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    cname = c.getString(2);
                    ename = c.getString(3);
                    caddress = c.getString(6);
                    daddress = c.getString(7);

                } while (c.moveToNext());
            }
        }
        Cursor c4 = sqLiteDatabase.rawQuery("Select * From Mark Where sid = "+id , null);
        if (c4 != null) {
            if (c4.moveToFirst()) {
                do {
                   mark = mark + c4.getInt(2);
                           counter = counter + 1;
                } while (c4.moveToNext());
            }
        }
        double averagemark = 0;
        if (counter >=1) {
            averagemark = mark / counter;
        }
        SchoolDetailData sdd = new SchoolDetailData(ename,cname,daddress,caddress,averagemark);

        sqLiteDatabase.close();
        return sdd;
    }

    public boolean marked(String sid,String ac) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select * From Mark where sid = " + sid + " AND Account = '" + ac + "'", null);
        boolean checker = false;
        if (c.getCount() == 0) {
            checker = true;
        }
        sqLiteDatabase.close();
        return checker;
    }

    public void addMark(String id,String ac,String mark) {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        ContentValues acvalue = new ContentValues();
        acvalue.put("sid", Integer.parseInt(id));
        acvalue.put("Account", ac);
        acvalue.put("mark",  Integer.parseInt(mark));
        long result = sqLiteDatabase.insert("Mark", null, acvalue);
        sqLiteDatabase.close();
    }

    public void addSchoolData(String ename,String cname,String eadd,String cadd, String X,String Y,String type){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("Select sid From School Order by sid DESC LIMIT 1" , null);
        int maxnewsid = 0;

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    maxnewsid = c.getInt(0);
                } while (c.moveToNext());
            }
        }
        maxnewsid = maxnewsid + 1;

        c = sqLiteDatabase.rawQuery("Select tid From Type where etype = '" + type + "' OR ctype = '" + type + "'" , null);
        int tid = 0;
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                 tid = c.getInt(0);
                } while (c.moveToNext());
            }
        }



        ContentValues schoolvalue = new ContentValues();
        schoolvalue.put("sid",maxnewsid);
        schoolvalue.put("tid",tid);
        schoolvalue.put("cname",cname);
        schoolvalue.put("ename",ename);
        schoolvalue.put("X",X);
        schoolvalue.put("Y",Y);
        schoolvalue.put("caddress",cadd);
        schoolvalue.put("eaddress",eadd);
        long result = sqLiteDatabase.insert("School", null, schoolvalue);
        sqLiteDatabase.close();
    }



}


