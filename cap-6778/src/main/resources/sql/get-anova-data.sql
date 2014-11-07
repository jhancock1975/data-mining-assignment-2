#!/bin/bash
mysql -pramanujan -h 192.168.1.55 -e "set @expTime=(select max(experimentStartTime) from ClassifierResults);select classifierClassName, filterClassName, featureSetSize, pAuc from ClassifierResults where experimentStartTime=@expTime" assign4 > anova-pauc.dat

mysql -pramanujan -h 192.168.1.55 -e "set @expTime=(select max(experimentStartTime) from ClassifierResults);select classifierClassName, filterClassName, featureSetSize, nAuc from ClassifierResults where experimentStartTime=@expTime" assign4 > anova-nauc.dat

mysql -pramanujan -h 192.168.1.55 -e "set @expTime=(select max(experimentStartTime) from ClassifierResults);select classifierClassName, filterClassName, featureSetSize, wAuc from ClassifierResults where experimentStartTime=@expTime" assign4 > anova-wauc.dat

echo 'data=read.csv("./anova-pauc.dat", sep="\t", header=TRUE); summary(aov(lm(data[[4]] ~ data[[1]]*data[[2]]*data[[3]])))  ' | R --no-save

echo 'data=read.csv("./anova-nauc.dat", sep="\t", header=TRUE); summary(aov(lm(data[[4]] ~ data[[1]]*data[[2]]*data[[3]])))  ' | R --no-save

echo 'data=read.csv("./anova-wauc.dat", sep="\t", header=TRUE); summary(aov(lm(data[[4]] ~ data[[1]]*data[[2]]*data[[3]])))  ' | R --no-save

