
		var se,m=0,h=0,s=0;  
		var v="";
	function second(){  

		if(s>0 && (s%60)==0){m+=1;s=0;}  
		if(m>0 && (m%60)==0){h+=1;m=0;}  
		if(h<10){t = "0"+h;}else{	t =h;	}
		t +=":";
		if(m<10){t += "0"+m;}else {t +=m;
			v  = "温馨提示：通话时长已经超过10分钟";
		}
		t +=":";
		if(s< 10){t += "0"+s;}else{t +=s;}
		$("#showtime").val(t);
		$("#message").html(v);
		s+=1;
	}  

//	window.setInterval(second,1000);
	function startclock(){se=window.setInterval(second(),1000);}  //这个函数是要放到按钮的click事件上的
	function pauseclock(){window.clearInterval(se);}    //这个函数是要放到按钮的click事件上的
	function stopclock(){window.clearInterval(se);ss=1;m=h=s=0;}    //这个函数是要放到按钮的click事件上的
	
	
	function second1(){  
		var v1  = "<div style='text-align: center;font-size:14px;font-weight: bold;color:red;'>签出时长已超过5分钟</div>";
		$.messager.show({
			title:'签出提醒',
			msg:v1,
			timeout:10000,
			showType:'slide'
		});
	}  
	function startclock1(){setTimeout(second1,300000);}  //这个函数是要放到按钮的click事件上的
	
	

	