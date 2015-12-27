# log4j2-tutorial
log4j 2.Xʹ�ý̳�
ʹ��slf4j��Ϊ��־�Ż���Ӧ��ֱ����slf4j���н�����ͨ��slf4j����log4j 2.X��
         app
          |
          |
      slf4j-api(slf4j�ӿ�)
          |
          |
   log4j-slf4j-impl(slf4j���䵽log4j 2.X)
          |
          |
      log4j-api(log4j 2.X�Ľӿڲ�)
          |
          |
      log4j-core(log4j 2.X��ʵ�ֲ�)

案例Slf4jAdapterTutorial演示的是如果项目中有多个logger实现，slf4j会根据类加载器加载的顺序，选择一个logger实现类。