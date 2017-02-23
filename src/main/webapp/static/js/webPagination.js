/**
 * author  yingjq
 * totalPageNum 总页数
 * curr   当前页
 * leftRight   当前页左右显示的页码
 * showPageLocation   显示页码的位置，dom的id标记(这个标记是ul,eg:<ul id="commodity_pagination" style="list-style-type:none;"></ul>)
 * requestMethodName  向后台请求分页的方法名，只有一个参数，为curr当前页
 */
var totalPageNumParam = null;
var currParam = null;
var leftRightParam = null;
var showPageLocationParam = null;
var requestMethodNameParam = null;
var oldCurrParam = null;
function pagination(totalPageNum, curr, leftRight, showPageLocation, requestMethodName){
	//调用后台获取数据方法
	//alert(oldCurrParam +"===" +curr)
	if(oldCurrParam != null && oldCurrParam != curr){//防止重复提交
		//requestMethodName(curr);
		getDataWithUsed(curr);//调用请求数据方法
	}
	
	//参数赋值
	totalPageNumParam = totalPageNum;
	oldCurrParam = curr;
	currParam = curr;
	leftRightParam = leftRight;
	showPageLocationParam = showPageLocation;
	requestMethodNameParam = requestMethodName;
	//先清空分页显示，再重新加载
	showPageLocation.empty();
	
	if(totalPageNum >0){
		//页码处理
		for(var j=0;j<totalPageNum;j++){
			if(leftRight >= curr ||  (curr+leftRight) >= totalPageNum){
				/**
				*左右显示页码大于当前页，有两种情况，1：左侧页数不足，2：右侧页数不足
				*当左侧页数不足时,显示当前页左侧所有页码
				*/
				//alert("leftRight="+leftRight+"curr="+curr+"totalPageNum="+totalPageNum+"j="+j)
				/*alert("11"+leftRight >= curr && ((curr+leftRight) < totalPageNum) && j<= curr+leftRight)
				alert("22"+leftRight < curr && ((curr+leftRight) >= totalPageNum) && j >= (curr-leftRight-1))
				alert("33"+leftRight > curr && ((curr+leftRight) > totalPageNum))
				alert("(curr+leftRight)="+(curr+leftRight)+"totalPageNum="+totalPageNum+(curr+leftRight) > totalPageNum)*/
				var currLeftRight = curr+leftRight;
				var curr_LeftRight = curr-leftRight;
				if(leftRight >= curr && currLeftRight < totalPageNum && j<= currLeftRight){
						if(j+1 == curr){
							//当前页加独特颜色
							var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px;background-color:blue'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px;color:white'>"+(j+1)+"</a></li>"; 
							showPageLocation.append(htmls);
						}else{
							if(j == curr + leftRight){
								//最右侧加省略号
								//右侧加省略号
								var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li><li style='float:left;height:30px;width:120px;margin-right:5px;padding-top:5px'><b>&nbsp;&nbsp;......</b><b><a href='javascript:void(0);' onclick='upPage("+(curr+1)+")' style='padding:6px;background-color:grey;color:white;margin:1px 5px 0px 10px'>下一页</a></b></li>"; 
								showPageLocation.append(htmls);
							}else{
								//正常不加
								var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li>"; 
								showPageLocation.append(htmls);
							}
					}
				}else if(leftRight < curr && currLeftRight >= totalPageNum && j >= (curr_LeftRight-1)){
				/**
				*当右侧页数不足时,显示当前页右侧所有页码
				*/
					if(j+1 == curr){
						//当前页加独特颜色
						var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px;background-color:blue'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px;color:white'>"+(j+1)+"</a></li>"; 
						showPageLocation.append(htmls);
					}else{
						if(j == curr - leftRight-1){
							//最左侧加省略号
							var htmls = "<li style='float:left;height:30px;width:120px;padding:5px'><a href='javascript:void(0);' onclick='downPage("+(curr-1)+");' style='padding:6px;background-color:grey;color:white;margin:1px 5px 0px 10px'>上一页</a><b>......</b></li><li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li>"; 
							showPageLocation.append(htmls);
						}else{
							//正常不加
							var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li>"; 
							showPageLocation.append(htmls);
						}
					}
				}else if(leftRight > curr && currLeftRight > totalPageNum){
					//当页数很少时，全显示
					//当右侧页数不足时,显示当前页右侧所有页码
					if(j+1 == curr){
						var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px;background-color:blue'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px;color:white'>"+(j+1)+"</a></li>"; 
						showPageLocation.append(htmls);
					}else{
						var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li>"; 
						showPageLocation.append(htmls);
					}
				}
			}else{
			/**
			*此时是正常状态，当前页左右都有足够的页数
			*/
				if(j >= curr-leftRight-1 && j<= curr+leftRight-1){
					if(j+1 == curr){
						var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px;background-color:blue'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px;color:white'>"+(j+1)+"</a></li>"; 
						showPageLocation.append(htmls);
					}else{
						//左侧加省略号
						if(j == curr-leftRight-1){
							var htmls = "<li style='float:left;height:30px;width:120px;padding:5px'><a href='javascript:void(0);' onclick='downPage("+(curr-1)+");' style='padding:6px;background-color:grey;color:white;margin:1px 5px 0px 10px'>上一页</a><b>......</b></li><li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li>"; 
							showPageLocation.append(htmls);
						}else if(j == curr+leftRight-1){
							//右侧加省略号
							var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li><li style='float:left;height:30px;width:120px;margin-right:5px;padding-top:5px'><b>&nbsp;&nbsp;......</b><b><a href='javascript:void(0);' onclick='upPage("+(curr+1)+")' style='padding:6px;background-color:grey;color:white;margin:1px 5px 0px 10px'>下一页</a></b></li>"; 
							showPageLocation.append(htmls);
						}else{
							//正常情况
							var htmls = "<li style='float:left;height:30px;width:30px;border:1px solid blue;margin-right:5px'><a style='width: 30px;height: 30px;display:block;text-align:center;cursor:pointer;padding-top:5px'>"+(j+1)+"</a></li>"; 
							showPageLocation.append(htmls);
						}
						
					}
				}
			}
		}
			
	}
}
//下一页
function upPage(obj){
	currParam = obj;
	pagination(totalPageNumParam, currParam, leftRightParam, showPageLocationParam,requestMethodNameParam);
}
//上一页
function downPage(obj){
	currParam = obj;
	pagination(totalPageNumParam, currParam, leftRightParam, showPageLocationParam,requestMethodNameParam);
}