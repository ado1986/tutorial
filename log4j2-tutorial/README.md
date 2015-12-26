# log4j2-tutorial
log4j 2.X使用教程
使用slf4j作为日志门户，应用直接与slf4j进行交互，通过slf4j调用log4j 2.X。
         app
          |
          |
      slf4j-api(slf4j接口)
          |
          |
   log4j-slf4j-impl(slf4j适配到log4j 2.X)
          |
          |
      log4j-api(log4j 2.X的接口层)
          |
          |
      log4j-core(log4j 2.X的实现层)

