#!/bin/bash
sleep 5
mongo --host mongo1 --eval "rs.initiate({_id : 'rs0','members' : [{_id : 0, host : 'mongo1:27017', priority : 2},{_id : 1, host : 'mongo2:27017'},{_id : 2, host : 'mongo3:27017'}]})"