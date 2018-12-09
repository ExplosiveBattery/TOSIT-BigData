#!/bin/bash
# data example   {"userId":2000,"day":"2017-03-01","begintime":1488326400000,"endtime":1488327000000,"data":[{"package":"com.browser1","activetime":480000},{"package":"com.browser","activetime":120000}]}
# 为了方便看到效果，一切相关数字都设置的不大
logNumOfOneDay=20 #设置每日上限用于跟换日期
dayAdd=0
userSum=10 #用户总数
packageSum=10  #应用总数
secondSum=$[24*3600] #秒总数
port=4444

while true; do
	for((i1=0;i1<$logNumOfOneDay;++i1)); do
		beginSecond=$[$RANDOM % $secondSum]
		activetime=$[$RANDOM % (($secondSum-$beginSecond) % 10800)] #认为一次不会超过3小时
		endSecond=$[$beginSecond + $activetime]
		echo {\
			\"userId\":$[$RANDOM % $userSum],\
			\"day\":\"$(/bin/date -d "+$dayAdd day" +%Y-%m-%d)\",\
			\"begintime\":$(/bin/date -d "+$dayAdd day +$beginSecond second" +%s)000,\
			\"endtime\":$(/bin/date -d "+$dayAdd day +$endSecond second" +%s)000,\
			\"data\":[\
				\{\"package\":\"com.app$[$RANDOM % $packageSum]\",\"activetime\":$[$activetime]000\}\
			]\
		} > /dev/tcp/172.18.0.13/$port

		beginSecond=$[$RANDOM % $secondSum]
		activetime=$[$RANDOM % (($secondSum-$beginSecond) % 10800)] #认为一次不会超过3小时
		endSecond=$[$beginSecond + $activetime]
		echo {\
			\"userId\":$[$RANDOM % $userSum],\
			\"day\":\"$(/bin/date -d "+$dayAdd day" +%Y-%m-%d)\",\
			\"begintime\":$(/bin/date -d "+$dayAdd day +$beginSecond second" +%s)000,\
			\"endtime\":$(/bin/date -d "+$dayAdd day +$endSecond second" +%s)000,\
			\"data\":[\
				\{\"package\":\"com.app$[$RANDOM % $packageSum]\",\"activetime\":$[$activetime]000\}\
			]\
		} > /dev/tcp/172.18.0.14/$port

		sleep 0.4
	done

	dayAdd=$[$dayAdd+1]
	sleep 1.5
done 
