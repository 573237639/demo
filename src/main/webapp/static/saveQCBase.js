var createFig = false;
function createCategory(){
	createFig = true;
	var checkBo="<div data-example-id='simple-nav-tabs' >";
		checkBo+="<p class='bs-example' style='padding-top:0px;'>服务流程*</p>";
		checkBo+="<input type='checkbox' id='selectAll'  value='全选' onclick='selectAll();'/> 全选";
		checkBo+="<div class='operator_div_1'>";
		
	for(var i=0;i<qcCategoryList.length;i++){
		var qc = qcCategoryList[i];
		if(qc[3]=="1"){
		checkBo+="<div class='float_left' style='padding-left:30px;padding-top:5px;'>";
		checkBo+="<input type='checkbox' id='"+qc[0]+"' name='checkBo' title='"+qc[1]+"' value='"+qc[0]+"' onclick='setSelectAll.call(this);'/><label for='"+qc[0]+"'>"+qc[1]+"</label>";
		checkBo+="</div>";
		}
	 }
	checkBo+="</div></div><div class='clear'></div><hr/>";
	var checkBo2="<div data-example-id='simple-nav-tabs'>";
	checkBo2+="<p class='bs-example' >专业水平*</p>";
	checkBo2+="<input type='checkbox' id='selectAll2'  value='全选' onclick='selectAll2();'/> 全选";
	checkBo2+="<div class='operator_div_2'>";
	for(var j=0;j<qcCategoryList.length;j++){
		var qc = qcCategoryList[j];
		if(qc[3]=="2"){
			checkBo2+="<div class='float_left' style='padding-left:30px;padding-top:5px;'>";
			checkBo2+="<input type='checkbox' id='"+qc[0]+"' name='checkBo2' title='"+qc[1]+"' value='"+qc[0]+"' onclick='setSelectAll2.call(this);'/><label for='"+qc[0]+"'>"+qc[1]+"</label>";
			checkBo2+="</div>";
		}
	}
	checkBo2+="</div></div><div class='clear'></div><hr align='center' width='94%'/>";
	checkBo+="<div class='context'><div id ='content_home_show'></div><div id='content_text'></div></div>";
			 
	return checkBo2+checkBo;
}
//专业水平         复选框事件     全选、取消全选的事件
function selectAll2(){
	if ($("#selectAll2").is(":checked")) {
		$('div.operator_div_2').find('input[name="checkBo2"]').each(function(){
			 var $checkbox2=$(this);
			 	checkboxId2=$checkbox2.attr('id');
			 	regionId2='#qc_'+checkboxId2;
			 	$region2=$(regionId2);
			 $region2.remove();
		 });
		$('div.operator_div_2').find('input[name="checkBo2"]').each(function(){
			$("div.operator_div_2 :checkbox").attr("checked", true);
			
				   if($(this).is(':checked') || $(this).prop('checked')){   //兼容IE
						$("#content_home_show").append("<div id='qc_"+$(this).attr("id")+"''></div>");
						$("#qc_"+$(this).attr("id")).append("<div style='padding-left:10px;font-weight:700;'>"+$(this).attr("title")+"</div>");
				   		var index = 0;
				   		for(var i=0;i<qcProList.length;i++){
							var qc = qcProList[i];
				   			if(qc[2]==$(this).attr("id") && qc[6]!='0'){
								$("#qc_"+$(this).attr("id")).append("<div id='sc_"+$(this).attr("id")+"' style='float:left;padding-left:30px;padding-top:5px;'></div>");
								$("#sc_"+$(this).attr("id")).append("<input type='hidden' id='"+qc[0]+"' title='"+qc[1]+"' value='"+qc[0]+"'/>");
								$("#sc_"+$(this).attr("id")).append("<div id='sl_"+qc[0]+"' style='padding: 0 0 5 30;text-align: right;font-size: 12px;'></div>");
								$("#sl_"+qc[0]).append(qc[1]+":<select id='sel_"+qc[0]+index+"' num_Check_Score='"+qc[4]+"' num_Must_Check_Bit='"+qc[5]+"'></select>");
								$("#sl_"+qc[0]).append("<font id='sel_"+qc[0]+index+"htm' class='font_htm' numBit='1'>(合格)</font>");
								var font_htm = $("#sel_"+qc[0]+index).attr("num_Must_Check_Bit");
								if(font_htm=="0"){
									$("#sel_"+qc[0]+index+"htm").css("color","green");
									$("#sel_"+qc[0]+index+"htm").html("(不必合格)");
								}
								for(var j=parseInt(qc[3]);j>=0;j--){
									$("#sel_"+qc[0]+index).append("<option value='"+j+"'>"+j+"</option>");
								}
								
								$("#sel_"+qc[0]+index).change(function(){
									var first_score = $(this).find('option:first').val();
									if($(this).attr("num_Must_Check_Bit")>0){
									    var check_score = $(this).attr("num_Check_Score");
									    var score = $(this).val();
										if(Number(check_score)>1){
											var htm = Number(check_score)<=score?"(合格)":"(不合格)";
											var color = Number(check_score)<=score?"green":"red";
											var numBit = Number(check_score)<=score?1:-1;
											$("#"+$(this).attr("id")+"htm").css("color",color);
											$("#"+$(this).attr("id")+"htm").html(htm);
											$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
										}else{
											var htm = (Number(check_score)*Number(first_score))<=Number(score)?"(合格)":"(不合格)";
											var color = (Number(check_score)*Number(first_score))<=Number(score)?"green":"red";
											var numBit = (Number(check_score)*Number(first_score))<=Number(score)?1:-1;
											$("#"+$(this).attr("id")+"htm").css("color",color);
											$("#"+$(this).attr("id")+"htm").html(htm);
											$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
										}
									}else{
										$("#"+$(this).attr("id")+"htm").css("color","green");
										$("#"+$(this).attr("id")+"htm").html("(不必合格)");
									}
								});
								index++;
				   			}
				   		}
						$("#qc_"+$(this).attr("id")).append("<div class='clear'></div><hr/>");
				   }
			   
		   });
	} else {
		 $("div.operator_div_2 :checkbox").attr("checked", false);
		 
		 $('div.operator_div_2').find('input[name="checkBo2"]').each(function(){
			 var $checkbox=$(this),
			 	checkboxId=$checkbox.attr('id'),
			 	regionId='#qc_'+checkboxId,
			 	$region=$(regionId);
			 
			 $region.remove();
		 });
	}
}
//专业水平  子复选框的事件
function setSelectAll2(){
	//当没有选中某个子复选框时，SelectAll取消选中
	var $checkbox=$(this),
	$container=$checkbox.parent('div').parent('div'),
	notSelectAll=$container.find(':checked').length!==$container.find(':checkbox').length;
	if (notSelectAll) {
		$("#selectAll2").removeAttr("checked");
		return;
	}
	$("#selectAll2").attr("checked", "checked");
}
//复选框事件
//全选、取消全选的事件
function selectAll(){
	if ($("#selectAll").is(":checked")) {
		$('div.operator_div_1').find('input[name="checkBo"]').each(function(){
			 var $checkbox=$(this),
			 	checkboxId=$checkbox.attr('id'),
			 	regionId='#qc_'+checkboxId,
			 	$region=$(regionId);
			 $region.remove();
		 });
		$('div.operator_div_1').find('input[name="checkBo"]').each(function(){
			$("div.operator_div_1 :checkbox").attr("checked", true);
			
				   if($(this).is(':checked') || $(this).prop('checked')){   //兼容IE
						$("#content_home_show").append("<div id='qc_"+$(this).attr("id")+"''></div>");
						$("#qc_"+$(this).attr("id")).append("<div style='padding-left:10px;font-weight:700;'>"+$(this).attr("title")+"</div>");
				   		var index = 0;
				   		for(var i=0;i<qcProList.length;i++){
							var qc = qcProList[i];
				   			if(qc[2]==$(this).attr("id") && qc[6]!='0'){
								$("#qc_"+$(this).attr("id")).append("<div id='sc_"+$(this).attr("id")+"' style='float:left;padding-left:30px;padding-top:5px;'></div>");
								$("#sc_"+$(this).attr("id")).append("<input type='hidden' id='"+qc[0]+"' title='"+qc[1]+"' value='"+qc[0]+"'/>");
								$("#sc_"+$(this).attr("id")).append("<div id='sl_"+qc[0]+"' style='padding: 0 0 5 30;text-align: right;font-size: 12px;'></div>");
								$("#sl_"+qc[0]).append(qc[1]+":<select id='sel_"+qc[0]+index+"' num_Check_Score='"+qc[4]+"' num_Must_Check_Bit='"+qc[5]+"'></select>");
								$("#sl_"+qc[0]).append("<font id='sel_"+qc[0]+index+"htm' class='font_htm' numBit='1'>(合格)</font>");
								var font_htm = $("#sel_"+qc[0]+index).attr("num_Must_Check_Bit");
								if(font_htm=="0"){
									$("#sel_"+qc[0]+index+"htm").css("color","green");
									$("#sel_"+qc[0]+index+"htm").html("(不必合格)");
								}
								for(var j=parseInt(qc[3]);j>=0;j--){
									$("#sel_"+qc[0]+index).append("<option value='"+j+"'>"+j+"</option>");
								}
								
								$("#sel_"+qc[0]+index).change(function(){
									var first_score = $(this).find('option:first').val();
									if($(this).attr("num_Must_Check_Bit")>0){
									    var check_score = $(this).attr("num_Check_Score");
									    var score = $(this).val();
										if(Number(check_score)>1){
											var htm = Number(check_score)<=score?"(合格)":"(不合格)";
											var color = Number(check_score)<=score?"green":"red";
											var numBit = Number(check_score)<=score?1:-1;
											$("#"+$(this).attr("id")+"htm").css("color",color);
											$("#"+$(this).attr("id")+"htm").html(htm);
											$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
										}else{
											var htm = (Number(check_score)*Number(first_score))<=Number(score)?"(合格)":"(不合格)";
											var color = (Number(check_score)*Number(first_score))<=Number(score)?"green":"red";
											var numBit = (Number(check_score)*Number(first_score))<=Number(score)?1:-1;
											$("#"+$(this).attr("id")+"htm").css("color",color);
											$("#"+$(this).attr("id")+"htm").html(htm);
											$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
										}
									}else{
										$("#"+$(this).attr("id")+"htm").css("color","green");
										$("#"+$(this).attr("id")+"htm").html("(不必合格)");
									}
								});
								index++;
				   			}
				   		}
						$("#qc_"+$(this).attr("id")).append("<div class='clear'></div><hr/>");
				   }
			   
		   });
	} else {
		 $("div.operator_div_1 :checkbox").attr("checked", false);
		 
		 $('div.operator_div_1').find('input[name="checkBo"]').each(function(){
			 var $checkbox=$(this),
			 	checkboxId=$checkbox.attr('id'),
			 	regionId='#qc_'+checkboxId,
			 	$region=$(regionId);
			 
			 $region.remove();
		 });
	}
}
//子复选框的事件
function setSelectAll(){
	//当没有选中某个子复选框时，SelectAll取消选中
	var $checkbox=$(this),
		$container=$checkbox.parent('div').parent('div'),
		notSelectAll=$container.find(':checked').length!==$container.find(':checkbox').length;
	if (notSelectAll) {
		$("#selectAll").removeAttr("checked");
		return;
	}
	$("#selectAll").attr("checked", "checked");
}

function _load(qcProList){
	$("#content_text").append("<div style='padding-left:10px;padding-top:10px;'><p style='float:left;width: 100;padding-top: 60;'>用户问题（200字）:</p><textarea  id='summary' onKeyDown='if (this.value.length>=200){event.returnValue=false}'></textarea></div>");
	$("#content_text").append("<div style='padding-left:10px;padding-top:10px;'><p style='float:left;width: 100;padding-top: 60;'>律师意见（200字）:</p><textarea  id='improve' onKeyDown='if (this.value.length>=200){event.returnValue=false}'></textarea></div>");
	$("#content_text").append("<div style='padding-left:10px;padding-top:10px;'><p style='float:left;width: 100;padding-top: 60;'>综合评价与改进建议（200字）:</p><textarea  id='comment' onKeyDown='if (this.value.length>=200){event.returnValue=false}'></textarea></div>");
	$("#content_text").append("<div style='padding-left:10px;padding-top:30px;'><p style='float:left;width: 100;padding-top: 60;'>备注（200字）:    </p><textarea  id='memo' onKeyDown='if (this.value.length>=200){event.returnValue=false}'></textarea></p></div>");
	$('div.operator_div_2').find('input[name="checkBo2"]').each(function(){
		   $(this).click(function(){
			   if($(this).is('div.operator_div_2 :checked') || $(this).prop('checked')){   //兼容IE
					$("#content_home_show").append("<div id='qc_"+$(this).attr("id")+"''></div>");
					$("#qc_"+$(this).attr("id")).append("<div style='padding-left:10px;font-weight:700;'>"+$(this).attr("title")+"</div>");
			   		var index = 0;
			   		for(var i=0;i<qcProList.length;i++){
						var qc = qcProList[i];
			   			if(qc[2]==$(this).attr("id") && qc[6]!='0'){
							$("#qc_"+$(this).attr("id")).append("<div id='sc_"+$(this).attr("id")+"' style='float:left;padding-left:30px;padding-top:5px;'></div>");
							$("#sc_"+$(this).attr("id")).append("<input type='hidden' id='"+qc[0]+"' title='"+qc[1]+"' value='"+qc[0]+"'/>");
							$("#sc_"+$(this).attr("id")).append("<div id='sl_"+qc[0]+"' style='padding: 0 0 5 30;text-align: right;font-size: 12px;'></div>");
							$("#sl_"+qc[0]).append(qc[1]+":<select id='sel_"+qc[0]+index+"' num_Check_Score='"+qc[4]+"' num_Must_Check_Bit='"+qc[5]+"'></select>");
							$("#sl_"+qc[0]).append("<font id='sel_"+qc[0]+index+"htm' class='font_htm' numBit='1'>(合格)</font>");
							var font_htm = $("#sel_"+qc[0]+index).attr("num_Must_Check_Bit");
							if(font_htm=="0"){
								$("#sel_"+qc[0]+index+"htm").css("color","green");
								$("#sel_"+qc[0]+index+"htm").html("(不必合格)");
							}
							for(var j=parseInt(qc[3]);j>=0;j--){
								$("#sel_"+qc[0]+index).append("<option value='"+j+"'>"+j+"</option>");
							}
							
							$("#sel_"+qc[0]+index).change(function(){
								var first_score = $(this).find('option:first').val();
								if($(this).attr("num_Must_Check_Bit")>0){
								    var check_score = $(this).attr("num_Check_Score");
								    var score = $(this).val();
									if(Number(check_score)>1){
										var htm = Number(check_score)<=score?"(合格)":"(不合格)";
										var color = Number(check_score)<=score?"green":"red";
										var numBit = Number(check_score)<=score?1:-1;
										$("#"+$(this).attr("id")+"htm").css("color",color);
										$("#"+$(this).attr("id")+"htm").html(htm);
										$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
									}else{
										var htm = (Number(check_score)*Number(first_score))<=Number(score)?"(合格)":"(不合格)";
										var color = (Number(check_score)*Number(first_score))<=Number(score)?"green":"red";
										var numBit = (Number(check_score)*Number(first_score))<=Number(score)?1:-1;
										$("#"+$(this).attr("id")+"htm").css("color",color);
										$("#"+$(this).attr("id")+"htm").html(htm);
										$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
									}
								}else{
									$("#"+$(this).attr("id")+"htm").css("color","green");
									$("#"+$(this).attr("id")+"htm").html("(不必合格)");
								}
							});
							index++;
			   			}
			   		}
					$("#qc_"+$(this).attr("id")).append("<div class='clear'></div><hr/>");
			   }else{
				   $("#qc_"+$(this).attr("id")).remove();
			   }
		   });
	   });
	$('div.operator_div_1').find('input[name="checkBo"]').each(function(){
		   $(this).click(function(){
			   if($(this).is('div.operator_div_1 :checked') || $(this).prop('checked')){   //兼容IE
					$("#content_home_show").append("<div id='qc_"+$(this).attr("id")+"''></div>");
					$("#qc_"+$(this).attr("id")).append("<div style='padding-left:10px;font-weight:700;'>"+$(this).attr("title")+"</div>");
			   		var index = 0;
			   		for(var i=0;i<qcProList.length;i++){
						var qc = qcProList[i];
			   			if(qc[2]==$(this).attr("id") && qc[6]!='0'){
							$("#qc_"+$(this).attr("id")).append("<div id='sc_"+$(this).attr("id")+"' style='float:left;padding-left:30px;padding-top:5px;'></div>");
							$("#sc_"+$(this).attr("id")).append("<input type='hidden' id='"+qc[0]+"' title='"+qc[1]+"' value='"+qc[0]+"'/>");
							$("#sc_"+$(this).attr("id")).append("<div id='sl_"+qc[0]+"' style='padding: 0 0 5 30;text-align: right;font-size: 12px;'></div>");
							$("#sl_"+qc[0]).append(qc[1]+":<select id='sel_"+qc[0]+index+"' num_Check_Score='"+qc[4]+"' num_Must_Check_Bit='"+qc[5]+"'></select>");
							$("#sl_"+qc[0]).append("<font id='sel_"+qc[0]+index+"htm' class='font_htm' numBit='1'>(合格)</font>");
							var font_htm = $("#sel_"+qc[0]+index).attr("num_Must_Check_Bit");
							if(font_htm=="0"){
								$("#sel_"+qc[0]+index+"htm").css("color","green");
								$("#sel_"+qc[0]+index+"htm").html("(不必合格)");
							}
							for(var j=parseInt(qc[3]);j>=0;j--){
								$("#sel_"+qc[0]+index).append("<option value='"+j+"'>"+j+"</option>");
							}
							
							$("#sel_"+qc[0]+index).change(function(){
								var first_score = $(this).find('option:first').val();
								if($(this).attr("num_Must_Check_Bit")>0){
								    var check_score = $(this).attr("num_Check_Score");
								    var score = $(this).val();
									if(Number(check_score)>1){
										var htm = Number(check_score)<=score?"(合格)":"(不合格)";
										var color = Number(check_score)<=score?"green":"red";
										var numBit = Number(check_score)<=score?1:-1;
										$("#"+$(this).attr("id")+"htm").css("color",color);
										$("#"+$(this).attr("id")+"htm").html(htm);
										$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
									}else{
										var htm = (Number(check_score)*Number(first_score))<=Number(score)?"(合格)":"(不合格)";
										var color = (Number(check_score)*Number(first_score))<=Number(score)?"green":"red";
										var numBit = (Number(check_score)*Number(first_score))<=Number(score)?1:-1;
										$("#"+$(this).attr("id")+"htm").css("color",color);
										$("#"+$(this).attr("id")+"htm").html(htm);
										$("#"+$(this).attr("id")+"htm").attr("numBit",numBit);
									}
								}else{
									$("#"+$(this).attr("id")+"htm").css("color","green");
									$("#"+$(this).attr("id")+"htm").html("(不必合格)");
								}
							});
							index++;
			   			}
			   		}
					$("#qc_"+$(this).attr("id")).append("<div class='clear'></div><hr/>");
			   }else{
				   $("#qc_"+$(this).attr("id")).remove();
			   }
		   });
	   });
};

function checkout(){
	var fig = true;
	var count=$('div.operator_div_1').find('input[type="checkbox"]').length;
	$('div.operator_div_1').find('input[type="checkbox"]').each(function(){
		if(!$(this).is(':checked') || !$(this).prop('checked')){
			count--;
		}
	});
	var count2=$('div.operator_div_2').find('input[type="checkbox"]').length;
	$('div.operator_div_2').find('input[type="checkbox"]').each(function(){
		if(!$(this).is(':checked') || !$(this).prop('checked')){
			count2--;
		}
	});
	if(count<1 && count2<1){
		return false;
	}
	return fig;
}

function projectScores(){
	var proArr = "";
	$('div.operator_div_1').find('input[type="checkbox"]').each(function(){
		if($(this).is(':checked') || $(this).prop('checked')){
			var index = 0;
			$("#sc_"+$(this).attr("id")).find('input[type="hidden"]').each(function(){
				var pro_id = $(this).attr("id");
				var pro_scores = $("#sel_"+$(this).attr("id")+index+" option:selected").val();
				var numBit = $("#sel_"+$(this).attr("id")+index+"htm").attr("numBit");
				proArr+="{pro_id:"+pro_id+",pro_scores:"+pro_scores+",numBit:"+numBit+"},";
				index++;
			});
		}
	});
	var proArr2 = "";
	$('div.operator_div_2').find('input[type="checkbox"]').each(function(){
		if($(this).is(':checked') || $(this).prop('checked')){
			var index = 0;
			$("#sc_"+$(this).attr("id")).find('input[type="hidden"]').each(function(){
				var pro_id = $(this).attr("id");
				var pro_scores = $("#sel_"+$(this).attr("id")+index+" option:selected").val();
				var numBit = $("#sel_"+$(this).attr("id")+index+"htm").attr("numBit");
				proArr2+="{pro_id:"+pro_id+",pro_scores:"+pro_scores+",numBit:"+numBit+"},";
				index++;
			});
		}
	});
	proArr=proArr.substring(0,proArr.length-1);
	proArr2=proArr2.substring(0,proArr2.length-1);
	if(proArr.length>0 && proArr2.length>0){
		proArr="["+proArr+","+proArr2+"]";
	}else if(proArr.length<1 && proArr2.length>0){
		proArr="["+proArr2+"]";
	}else{
		proArr="["+proArr+"]";
	}
	return proArr;
}
