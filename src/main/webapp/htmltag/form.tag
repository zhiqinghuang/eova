<form id="${id}" method="post" class="form">
	<table class="table" style="width: 100%;">
		<%for(item in items){%>
			<%if(item.type == "自增框"){%>
				<%// 只有编辑时显示自增框 %>
				<%if(isTrue(isUpdate!)){%>
				<tr>
					<th title="${item.en}">${item.cn}</th>
					<td><div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-auto"></div></td>
                    <% // <#auto id="${item.en}" name="${item.en}" value="${item.value!}"/>%>
				</tr>
				<%}%>
			<%continue;}%>
			
			<%// 该字段是否允许编辑%>
			<%if(isTrue(isUpdate!)){%>
				<%if(isTrue(item.isUpdate)){%>
				<tr>
					<th title="${item.en}">${item.cn}</th>
					<td>
					<%if(item.type == "下拉框"){%>
                        <div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-combo"></div>
                        <script>
                        $('#${item.en}').eovacombo({
                            url: '/widget/comboJson/${item.objectCode}-${item.en}',
                            valueField : 'ID',
                            textField : 'CN'
                        });
                        </script>
					<%} else if(item.type == "查找框"){%>
                        <div id="${item.en}" name="${item.en}" value="${item.value!}" code="${item.objectCode}" class="eova-find"
                                url="/widget/find?code=${item.objectCode}&en=${item.en}"></div>
					<%} else if(item.type == "时间框"){%>
                        <div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-time"></div>
					<%} else if(item.type == "文本域"){%>
						<#texts id="${item.en}" name="${item.en}" value="${item.value!}" style="width:70%;height:50px;" />
					<%} else if(item.type == "复选框"){%>
						<#check id="${item.en}" name="${item.en}" value="${item.value!}" />
					<%}else if(item.type == "图标框"){%>
                        <div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-icon"></div>
					<%} else {// 默认为文本框<#text id="${item.en}" name="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />%>
                        <div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-text"></div>
					<%}%>
					</td>
				</tr>
				<%}%>
			<%} else {%>
				<%if(isTrue(item.isAdd)){%>
				<tr>
					<th title="${item.en}">${item.cn}</th>
					<td>
					<%if(item.type == "下拉框"){%>
						<div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-combo"></div>
                        <script>
                        $('#${item.en}').eovacombo({
                            url: '/widget/comboJson/${item.objectCode}-${item.en}',
                            valueField : 'ID',
                            textField : 'CN'
                        });
                        </script>
					<%} else if(item.type == "查找框"){%>
						<div id="${item.en}" name="${item.en}" value="${item.value!}" code="${item.objectCode}" class="eova-find"
                                url="/widget/find?code=${item.objectCode}&en=${item.en}"></div>
					<%} else if(item.type == "时间框"){%>
						<div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-time"></div>
					<%} else if(item.type == "文本域"){%>
						<#texts id="${item.en}" name="${item.en}" value="${item.value!}" style="width:70%;height:50px;" />
					<%} else if(item.type == "复选框"){%>
						<#check id="${item.en}" name="${item.en}" value="${item.value!}" />
					<%}else if(item.type == "图标框"){%>
						<div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-icon"></div>
					<%} else {// 默认为文本框<#text id="${item.en}" name="${item.en}" value="${item.value!}" isNoN="${item.isNotNull!}" />%>
                        <div id="${item.en}" name="${item.en}" value="${item.value!}" class="eova-text"></div>
					<%}%>
					</td>
				</tr>
				<%}%>
			<%}%>
		<%}%>
	</table>
</form>