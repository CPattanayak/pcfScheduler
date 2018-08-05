way to run the job
cf create-job batch-example ReLaunch #".java-buildpack/open_jdk_jre/bin/java org.springframework.boot.loader.JarLauncher"
cf schedule-job ReLaunch #"20 9 ? * * "
cf run-job ReLaunch  #(To Test the job)
