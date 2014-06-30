db.posts.aggregate([{$unwind:'$comments'},{$group:{_id:'$comments.author',total:{$sum:1}}},{$sort:{total:-1}},{limit:1}])

