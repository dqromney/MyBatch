# MyBatch
A Spring 4 Batch project to learn with. 

        2017-01-30 16:13:26.720  INFO 26352 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from URL [file:/Users/dqromney/projects/stg/MyBatch/target/classes/schema-all.sql]
        2017-01-30 16:13:26.723  INFO 26352 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from URL [file:/Users/dqromney/projects/stg/MyBatch/target/classes/schema-all.sql] in 3 ms.
        2017-01-30 16:13:27.068  INFO 26352 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executing SQL script from class path resource [org/springframework/batch/core/schema-hsqldb.sql]
        2017-01-30 16:13:27.074  INFO 26352 --- [           main] o.s.jdbc.datasource.init.ScriptUtils     : Executed SQL script from class path resource [org/springframework/batch/core/schema-hsqldb.sql] in 5 ms.
        2017-01-30 16:13:27.163  INFO 26352 --- [           main] o.s.j.e.a.AnnotationMBeanExporter        : Registering beans for JMX exposure on startup
        2017-01-30 16:13:27.172  INFO 26352 --- [           main] o.s.b.a.b.JobLauncherCommandLineRunner   : Running default command line with: []
        2017-01-30 16:13:27.232  INFO 26352 --- [           main] o.s.b.c.r.s.JobRepositoryFactoryBean     : No database type set, using meta data indicating: HSQL
        2017-01-30 16:13:27.346  INFO 26352 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : No TaskExecutor has been set, defaulting to synchronous executor.
        2017-01-30 16:13:27.390  INFO 26352 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=importUserJob]] launched with the following parameters: [{run.id=1}]
        2017-01-30 16:13:27.403  INFO 26352 --- [           main] o.s.batch.core.job.SimpleStepHandler     : Executing step: [step1]
        2017-01-30 16:13:27.417  INFO 26352 --- [           main] hello.PersonItemProcessor                : Converting (Person{lastName='Doe', firstName='Jill'}) into (Person{lastName='DOE', firstName='JILL'})
        2017-01-30 16:13:27.417  INFO 26352 --- [           main] hello.PersonItemProcessor                : Converting (Person{lastName='Doe', firstName='Joe'}) into (Person{lastName='DOE', firstName='JOE'})
        2017-01-30 16:13:27.417  INFO 26352 --- [           main] hello.PersonItemProcessor                : Converting (Person{lastName='Doe', firstName='Justin'}) into (Person{lastName='DOE', firstName='JUSTIN'})
        2017-01-30 16:13:27.417  INFO 26352 --- [           main] hello.PersonItemProcessor                : Converting (Person{lastName='Doe', firstName='Jane'}) into (Person{lastName='DOE', firstName='JANE'})
        2017-01-30 16:13:27.417  INFO 26352 --- [           main] hello.PersonItemProcessor                : Converting (Person{lastName='Doe', firstName='John'}) into (Person{lastName='DOE', firstName='JOHN'})
        2017-01-30 16:13:27.425  INFO 26352 --- [           main] hello.JobCompletionNotificationListener  : !!! JOB FINISHED! Time to verify the results
        2017-01-30 16:13:27.426  INFO 26352 --- [           main] hello.JobCompletionNotificationListener  : Found <Person{lastName='DOE', firstName='JILL'}> in the database.
        2017-01-30 16:13:27.426  INFO 26352 --- [           main] hello.JobCompletionNotificationListener  : Found <Person{lastName='DOE', firstName='JOE'}> in the database.
        2017-01-30 16:13:27.426  INFO 26352 --- [           main] hello.JobCompletionNotificationListener  : Found <Person{lastName='DOE', firstName='JUSTIN'}> in the database.
        2017-01-30 16:13:27.426  INFO 26352 --- [           main] hello.JobCompletionNotificationListener  : Found <Person{lastName='DOE', firstName='JANE'}> in the database.
        2017-01-30 16:13:27.426  INFO 26352 --- [           main] hello.JobCompletionNotificationListener  : Found <Person{lastName='DOE', firstName='JOHN'}> in the database.
        2017-01-30 16:13:27.428  INFO 26352 --- [           main] o.s.b.c.l.support.SimpleJobLauncher      : Job: [FlowJob: [name=importUserJob]] completed with the following parameters: [{run.id=1}] and the following status: [COMPLETED]
        2017-01-30 16:13:27.429  INFO 26352 --- [           main] hello.Application                        : Started Application in 2.197 seconds (JVM running for 2.616)
        2017-01-30 16:13:27.430  INFO 26352 --- [       Thread-2] s.c.a.AnnotationConfigApplicationContext : Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@3d0f8e03: startup date [Mon Jan 30 16:13:25 MST 2017]; root of context hierarchy
        2017-01-30 16:13:27.430  INFO 26352 --- [       Thread-2] o.s.j.e.a.AnnotationMBeanExporter        : Unregistering JMX-exposed beans on shutdown