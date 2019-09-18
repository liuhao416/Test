package com.util

/**
  * Description：xxxx<br/>
  * Copyright (c) ， 2018， Jansonxu <br/>
  * This program is protected by copyright laws. <br/>
  * Date： 2019年09月17日
  *
  * @author 刘皓
  * @version : 1.0
  */
object String2Type {


    def toInt(str: String): Int = {
      try {
        str.toInt
      } catch {
        case _: Exception => 0
      }
    }

    def toDouble(str: String): Double = {
      try {
        str.toDouble
      } catch {
        case _: Exception => 0.0
      }
    }
}
