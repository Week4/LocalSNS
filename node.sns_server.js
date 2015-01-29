 var mysql = require('mysql');
 var fs = require('fs');
 var http = require('http');
 // var bodyParser = require('bodyParser');
 // var urlencodedParser = bodyParser.urlencoded({ extended : false});
 var db = mysql.createConnection({user:'root',password:'1212',database:'sns'});
 var qs = require('querystring');

function parseReq(req, cb) {
	var body = '';
	req.setEncoding('utf8');
	req.on('data',function(chunk) {body += chunk});
	req.on('end', function() {
		var data = qs.parse(body);
		cb(data);
	});
};

function login(req, res){
	parseReq(req, function (data) {
		db.query("SELECT * FROM user_list WHERE ID = ? and PW = ?",[data.id, data.pw],function(err,result){
		console.log(data);
		if(err){
			console.error(err);
		  	throw err;
			}
		if(result.length==0){ 
			console.log('none');
			res.write('none');			
			res.end();
		}
		else {
			console.log('exists');
			res.end(result[0].ID);
		}
		console.log(result);
		});
	});
};

function signup(req, res){
	parseReq(req, function (data) {
		db.query("SELECT * FROM user_list WHERE ID = ? and PW = ?",[data.id, data.pw],function(err,result){
		console.log(data);
		if(err){
			console.error(err);
		  	throw err;
			}
		if(result.length!=0)
			res.end('already_exists');
		else {
			db.query('INSERT INTO user_list (ID,PW) VALUES (?,?)',[data.id, data.pw]);
   			res.end('welcome, ' + data.id);
			}
		});
	});
};

 var server = http.createServer(function(req,res){
 	console.log("server");
 	console.log(req.body);

 	switch (req.method){
 		case 'POST':
	 		console.log("POST");
	 		switch(req.url){
	 			
	 			case '/login':
					console.log("login");
					login(req, res);
	 				break;
	 			
	 			case '/signup':
		 			console.log("signup");
					signup(req, res);
	 				break;
	 		}
	 	break;
	 	

	 	case 'GET':
		 	console.log("get");
	 		switch(req.url) {
	 		}
	 	break;	
 	}
 })

 server.listen(9000,function(){
 	console.log('9000 start');
 });
