<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=9" >
<tags:title title="权限列表"/>
    <link rel="stylesheet" href="${ctxStatic}/bootstrap-3.3.5/css/bootstrap.min.css">
<%@ include file="/WEB-INF/views/include/header.jsp"%>
<link rel="stylesheet" href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css" type="text/css" />
<style type="text/css">
			.table {
				font-size:13px;
				border-collapse:collapse;
				border-top: 1px solid #d1d1d1;
				border-left: 1px solid #d1d1d1;
			}
			
			.table td, .table th {
				vertical-align: middle;
				border-bottom: 1px solid #d1d1d1;
				border-right: 1px solid #d1d1d1;
				padding:3px;
				text-align: center;
				line-height:25px;
			}
			
			.table th {
				text-align: center;
				background-color:#4682b4;
				color:#ffffff;
				font-size:13px;
				font-weight: bold;
			}
			
			.inputTable {
				font-size:13px;
				border-collapse:collapse; 
				border-top: 1px solid #d1d1d1;
				border-left: 1px solid #d1d1d1;
			}
			
			.inputTable td, .inputTable th {
				vertical-align: middle;
				text-align:center;
				border-bottom: 1px solid #d1d1d1;
				border-right: 1px solid #d1d1d1;
				line-height:20px;
			}
			
			.inputTable th {
				text-align: center;
				background-color:#4682b4;
				color:#ffffff;
				font-size:13px;
				font-weight: bold;
			}
			.error{color:red;}
			a:hover {text-decoration: none;color:#FFF}
			body {
				     overflow-x : hidden;  
				     font-family: PingHei,STHeitiSC-Light,"Apple LiGothic","microsoft yahei","Arial Normal",Arial;
					 font-size: 14px; 
				} 
		</style>
</head>
<body>
		<SCRIPT type="text/javascript" >
			var zTree;
			var demoIframe;
			var setting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false
				},
				data: {
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: ""
					}
				},
				callback: {
					beforeClick: function(treeId, treeNode) {
						demoIframe.attr("src","${ctx}/sys/menu/menuinfo?id="+treeNode.id);
						var zTree = $.fn.zTree.getZTreeObj("tree");
						zTree.selectNode(zTree.getNodeByParam("id", treeNode.id));
						return true;
					},
					onExpand: function(){
						document.getElementById('treeContainer').click();
					},
					onCollapse: function(){
						document.getElementById('treeContainer').click();
					}
				}
			};

	function initTree(menuid){
		$.get("${ctx}/sys/menu/tree", function (data, textStatus){
			var t = $("#tree");
			t = $.fn.zTree.init(t, setting, JSON.parse(data));
			demoIframe = $("#menuDetailIframe");
			var zTree = $.fn.zTree.getZTreeObj("tree");
			zTree.selectNode(zTree.getNodeByParam("id", menuid));		
		});
	}

  </SCRIPT>

<div class="page-content ml100">
	<div class="page-header">
		<h1>
			菜单管理
		</h1>
	</div><!-- /.page-header -->
				<!-- PAGE CONTENT BEGINS -->
					 <table class="table" >
					      <tr>
					        <td width="200"  id="treeContainer"
					          style="text-align: left; vertical-align: top; padding-left: 8px;">
								<ul id="tree" class="ztree" style="width:100%; height：100%;"></ul>
					        </td>
					        <td id="menuDetail" style="padding: 0px">
					       		<iframe id="menuDetailIframe" name="menuDetailIframe" scrolling="no" frameborder="0" width="98%"
					            onload="addEvt(this); document.getElementById('treeContainer').click();" src="${ctx}/sys/menu/menuinfo?id=0"></iframe>
					        </td>
					      </tr>
					    </table>
</div>
<script type="text/javascript">
function addEvt(ifr){
	  var doc=ifr.contentWindow.document;
	  doc.onclick=function(){
	    //ifr.style.height=(document.all?doc.body.scrollHeight:doc.body.offsetHeight)+'px';
	    //ifr.style.height=doc.body.firstChild.offsetHeight;//有根节点可以使用根节点，兼容性比较好，opera，safari，google的浏览器都可以兼容，如果使用body.offsetHeight只在ff下有效果
		  ifr.height = getContentHeight(ifr);
		  //document.getElementById('treeContainer').click();
	  }
	  ifr.height=getContentHeight(ifr);
}
//获得网页内容高度
function getContentHeight(obj)
{
     //可见高
  var ContentHeight=obj.contentWindow.document.body.scrollHeight;//其它浏览器默认值

    if(navigator.userAgent.indexOf("Chrome")!=-1)
    {
        ContentHeight= obj.contentWindow.document.body.clientHeight+40;
    }

   if(navigator.userAgent.indexOf("Firefox")!=-1)
    {
       ContentHeight=obj.contentWindow.document.body.offsetHeight+40;
    }  
    return ContentHeight;
}
</script>

<script src="${ctxStatic}/bootstrap-3.3.5/js/bootstrap.min.js"></script>
<script src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.core-3.5.min.js"></script>