package com.location

import java.util.Properties

import com.typesafe.config.ConfigFactory
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
  * 要求一：
  * 将统计的结果输出成 json 格式，并输出到磁盘目录。
  * {"ct":943,"provincename":"内蒙古自治区","cityname":"阿拉善盟"}
  * {"ct":578,"provincename":"内蒙古自治区","cityname":"未知"}
  * {"ct":262632,"provincename":"北京市","cityname":"北京市"}
  * {"ct":1583,"provincename":"台湾省","cityname":"未知"}
  * {"ct":53786,"provincename":"吉林省","cityname":"长春市"}
  * {"ct":41311,"provincename":"吉林省","cityname":"吉林市"}
  * {"ct":15158,"provincename":"吉林省","cityname":"延边朝鲜族自治州"}
  */
object provincecity {

  def main(args: Array[String]): Unit = {
    //判断路径是否正确
//    if (args != 1){
//      println("输入错误，请重新输入！")
//      sys.exit()
//    }

    //创建sparksession对象
    val  sparkSession=SparkSession
      .builder()
      .appName("provincecity")
      .master("local[*]")
      //序列化设置
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .getOrCreate()

    //val  Array(inputPath)=args
    //读取数据
     val dataFrame: DataFrame = sparkSession.read.parquet("F:\\data\\project.parquet")

    //使用sql编程对数据进行分析
     //建立一个临时表
    //createOrReplaceTempViwe 创建同名的表会进行覆盖
    //dataFrame.createOrReplaceTempView("test")
    dataFrame.createTempView("test")
   //写sql
    val frame: DataFrame = sparkSession.sql("select provincename,cityname  ,count(*) ct from test group by provincename,cityname ")

//    //将数据写入到json中
//    frame.write.partitionBy("provincename","cityname").json("F:\\data\\parquet.json")
//

    //将数据写入到MySQL中去
    //通过config文件加载相应的配置信息
    val  load =ConfigFactory.load()

    val  properties=new  Properties()
    properties.setProperty("user",load.getString("jdbc.user"))
    properties.setProperty("password",load.getString("jdbc.password"))
    //将信息放到MySQL中
    frame.write.mode(SaveMode.Append)
      .jdbc(load.getString("jdbc.url"),load.getString("jdbc.tableName"),properties)


    sparkSession.stop()
  }
}
