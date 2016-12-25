sh.addShard("shardone:27100")
sh.addShard("shardtwo:27100")
use admin
sh.enableSharding("coursework")
db.runCommand({shardCollection: "coursework.article", key: {_id: 1}});
