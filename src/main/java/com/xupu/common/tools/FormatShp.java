package com.xupu.common.tools;

import com.alibaba.fastjson.JSONObject;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geojson.feature.FeatureJSON;
import org.opengis.feature.simple.SimpleFeature;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jzxh
 * @description 读取shp格式文件，转成json格式数据
 * @data 2018年10月29日 上午9:37:05
 */
public class FormatShp {

    /**
     * String shpPath="C:\\Users\\Administrator\\Desktop\\tpktest\\ZJD.shp";
     *
     * @param path
     * @return
     * @throws MalformedURLException
     */
    public static List<String> shp2json(String path) {

        List<String> jsons = new ArrayList<>();
        FeatureJSON fJson = new FeatureJSON();
        String shpPath = path;
        File file = new File(shpPath);
        ShapefileDataStore store = null;
        JSONObject json = new JSONObject();
        try {
            store = new ShapefileDataStore(file.toURL());
            Charset charset = Charset.forName("UTF-8");
            store.setCharset(charset);
            String typeName = store.getTypeNames()[0];
            SimpleFeatureSource featureSource = null;
            featureSource = store.getFeatureSource(typeName);
            SimpleFeatureCollection collection = featureSource.getFeatures();
            SimpleFeatureIterator iterator = collection.features();
            while (iterator.hasNext()) {
                SimpleFeature feature = iterator.next();
                StringWriter writer = new StringWriter();
                fJson.writeFeature(feature, writer);
                jsons.add(writer.toString());
                //json=JSONObject.parseObject(writer.toString());
                //array.add(json);//使用jsonArray可以把所有数据转成一条；不使用，
                //下方输出只会输出一条JSON数据，如需存入数据库，改写此方法，在实现类里迭代。
            }
            iterator.close();
            //jsons.add(json);

            //sb.append("}");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jsons;
    }

    public static void WriteStringToFile(String string) {
    /*    System.out.println(string);
        String filePath="E:\\shp\\point.geojson";
        try {
            File file = new File(filePath);
            PrintStream ps = new PrintStream(new FileOutputStream(file));
            ps.append(string);// 在已有的基础上添加字符串
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) {

        shp2json("");

    }
}