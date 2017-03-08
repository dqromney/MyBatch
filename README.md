# MyBatch
A Spring 4 Batch project to learn with. 

## Partial log of the run: 

* java -jar target/gs-batch-processing-0.1.0.jar

          .   ____          _            __ _ _
         /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
        ( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
         \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
          '  |____| .__|_| |_|_| |_\__, | / / / /
         =========|_|==============|___/=/_/_/_/
         :: Spring Boot ::        (v1.4.4.RELEASE)
        
        2017-03-08 09:55:20.324  INFO 79289 --- [           main] com.stg.Application                      : Starting Application on DQR-2.local with PID 79289 (/Users/dqromney/projects/stg/MyBatch/target/classes started by dqromney in /Users/dqromney/projects/stg/MyBatch)
        2017-03-08 09:55:20.328  INFO 79289 --- [           main] com.stg.Application                      : The following profiles are active: local
        2017-03-08 09:55:20.448  INFO 79289 --- [           main] s.c.a.AnnotationConfigApplicationContext : Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@7bd4937b: startup date [Wed Mar 08 09:55:20 MST 2017]; root of context hierarchy
        2017-03-08 09:55:21.439  WARN 79289 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.stepScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
        2017-03-08 09:55:21.451  WARN 79289 --- [           main] o.s.c.a.ConfigurationClassEnhancer       : @Bean method ScopeConfiguration.jobScope is non-static and returns an object assignable to Spring's BeanFactoryPostProcessor interface. This will result in a failure to process annotations such as @Autowired, @Resource and @PostConstruct within the method's declaring @Configuration class. Add the 'static' modifier to this method to avoid these container lifecycle issues; see @Bean javadoc for complete details.
        2017-03-08 09:55:22.450  INFO 79289 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/batch/core/schema-mysql.sql]
        2017-03-08 09:55:22.464  INFO 79289 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/batch/core/schema-mysql.sql] in 14 ms.
        2017-03-08 09:55:22.583  INFO 79289 --- [           main] o.f.core.internal.util.VersionPrinter    : Flyway 3.2.1 by Boxfuse
        2017-03-08 09:55:22.590  INFO 79289 --- [           main] o.f.c.i.dbsupport.DbSupportFactory       : Database: jdbc:mysql://localhost:3306/quotes?useSSL=false (MySQL 5.7)
        2017-03-08 09:55:22.633  INFO 79289 --- [           main] o.f.core.internal.command.DbValidate     : Validated 1 migration (execution time 00:00.021s)
        2017-03-08 09:55:22.669  INFO 79289 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema `quotes`: 1
        2017-03-08 09:55:22.677  INFO 79289 --- [           main] o.f.core.internal.command.DbMigrate      : Schema `quotes` is up to date. No migration necessary.
        2017-03-08 09:55:22.869  INFO 79289 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
        2017-03-08 09:55:22.891  INFO 79289 --- [           main] o.s.b.a.b.JobLauncherCommandLineRunner   : Running default command line with: []
        2017-03-08 09:55:22.905  INFO 79289 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: MYSQL
        2017-03-08 09:55:23.123  INFO 79289 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
        2017-03-08 09:55:23.208  INFO 79289 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=processEodJob]] launched with the following parameters: [{run.id=2}]
        2017-03-08 09:55:23.234  INFO 79289 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [downloadStep]
        2017-03-08 09:55:24.181  INFO 79289 --- [           main] c.s.c.j.Q.QuotesBatchConfiguration       : DatatableBuildDownload(link=https://duf9k3nptkna3.cloudfront.net/WIKI/PRICES/WIKI_PRICES_212b326a081eacca455e13140d7bb9db.zip?api_key=iDys-FbJZTMWF56LXBie&snapshot_time=2017-03-08+01%3A00%3A10+UTC&Expires=1488992244&Signature=TZHkqTMzCnzOxSbLyfBXdLQvv9L11ERT405v2L4QmJF5l-miSva3Ebul0WAAQzjLSxn-5hLnujcUBHTAJ9ZZs9H5fruUrw62eXTqSuWYmDH9sdQNzP5OHfu6Cb-U4e34pV-ADydjaaPQ-o~pgPsjtzm2uaIEq5Xnj557-AGuYJTQLdFDnrawW2OhDXi~HqDkN~d3n-Nr5miy9gMBKoO8liN8YCwri9FvG8u9lBqhhZCQtULziGft495IpMT7BQBiTT1NZk9y07HBgxFaoCjnK9GBA6tiGYeHQzGx29--7yQtXcfq1vyxmOuLa95xXZy82-2LBrClI84BH7LT5enIFA__&Key-Pair-Id=APKAJ22GRK7AQ76GWFJQ, status=fresh, dataSnapshotTime=2017-03-08 01:00:10 UTC, lastRefreshedTime=2017-03-08 00:35:36 UTC)
        2017-03-08 09:57:12.673  INFO 79289 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [decompressStep]
        2017-03-08 09:58:02.898  INFO 79289 --- [           main] .s.c.j.JobCompletionNotificationListener : !!! JOB FINISHED! Time to verify the results
        2017-03-08 09:58:02.902  INFO 79289 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=processEodJob]] completed with the following parameters: [{run.id=2}] and the following status: [COMPLETED]
        2017-03-08 09:58:02.904  INFO 79289 --- [           main] com.stg.Application                      : Started Application in 163.154 seconds (JVM running for 163.777)
        2017-03-08 09:58:02.904  INFO 79289 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@7bd4937b: startup date [Wed Mar 08 09:55:20 MST 2017]; root of context hierarchy
        2017-03-08 09:58:02.906  INFO 79289 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdownporter        : Unregistering JMX-exposed beans on shutdown
