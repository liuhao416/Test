package com.location

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * Description：xxxx<br/>
  * Copyright (c) ， 2018， Jansonxu <br/>
  * This program is protected by copyright laws. <br/>
  * Date： 2019年09月18日
  *
  * @author 刘皓
  * @version : 1.0
  */
object GeographicalDistribution {

  def main(args: Array[String]): Unit = {
     //创建sparksession对象
    val  spark=SparkSession.builder()
      .config("spark.serializer","org.apache.spark.serializer.KryoSerializer")
      .config("spark.sql.parquet.compression.codec","snappy")
      .appName("GeographicalDistribution")
      .master("local[*]")
      .getOrCreate()

    //读取数据
    val df: DataFrame = spark.read.parquet("F:\\data\\project.parquet")

    //requestmode	processnode	iseffective	isbilling	isbid	iswin	adorderid,winprice,adpayment
    import spark.implicits._
    df.map(line=>{
      val requestmode: Int = line.getAs[Int]("requestmode")
      val processnode: Int = line.getAs[Int]("processnode")
      val iseffective: Int = line.getAs[Int]("iseffective")
      val isbilling: Int = line.getAs[Int]("isbilling")
      val isbid: Int = line.getAs[Int]("isbid")
      val iswin: Int = line.getAs[Int]("iswin")
      val adorderid: Int = line.getAs[Int]("adorderid")
      val winprice: Int = line.getAs[Int]("winprice")
      val adpayment: Int = line.getAs[Int]("adpayment")
    })
  }
}
