       $(document).ready(function() {
          changeCriteria();
          $("#searchType").change(changeCriteria);
          
          $("#submitbtn").click(function() {
             var searchType = $("#searchType").val();
             if(searchType==1 && $("#identifier").val()=="") {
                alert("please input identifier");
                return false;
             }
             else if(searchType==2 && $("#name").val()=="") {
                alert("please input name");
                return false;
             }
             
            
          });
          
          function changeCriteria() {
             var searchType = $("#searchType").val();
             if(searchType==0) {
                $("#criteria1").hide();
                $("#criteria2").hide();
                $("#criteria3").hide();
                $("#criteria4").hide();
   			 	$("#criteria5").hide();
   			 	$("#criteria6").hide();
             }
             else if(searchType==1) {
                $("#criteria1").show();
                $("#criteria2").hide();
                $("#criteria3").hide();
                $("#criteria4").hide();
                $("#criteria5").hide();
   			 	$("#criteria6").hide();
             }
             else if(searchType==2) {
                $("#criteria1").hide();
                $("#criteria2").show();
                $("#criteria3").hide();
                $("#criteria4").hide();
   			 	$("#criteria5").hide();
   			 	$("#criteria6").hide();
             }
             else if(searchType==3) {
                 $("#criteria1").hide();
                 $("#criteria2").hide();
                 $("#criteria3").show();
                 $("#criteria4").hide();
    			 $("#criteria5").hide();
    			 $("#criteria6").hide();
              }
             else if(searchType==4) {
                 $("#criteria1").hide();
                 $("#criteria2").hide();
                 $("#criteria3").hide();
                 $("#criteria4").show();
     			 $("#instrumentGroupId").show();
    			 $("#instrumentTypeId").hide();
    			 $("#instrumentSubtypeId").hide();
    			 $("#criteria5").hide();
    			 $("#criteria6").hide();
              }
             else if(searchType==5) {
                 $("#criteria1").hide();
                 $("#criteria2").hide();
                 $("#criteria3").hide();
                 $("#criteria4").show();
     			 $("#instrumentGroupId").show();
    			 $("#instrumentTypeId").show();
    			 $("#instrumentSubtypeId").hide();
    			 $("#criteria5").hide();
    			 $("#criteria6").hide();
              }
             else if(searchType==6) {
                 $("#criteria1").hide();
                 $("#criteria2").hide();
                 $("#criteria3").hide();
                 $("#criteria4").show();
     			 $("#instrumentGroupId").show();
    			 $("#instrumentTypeId").show();
    			 $("#instrumentSubtypeId").show();
    			 $("#criteria5").hide();
    			 $("#criteria6").hide();
              }
             else if(searchType==7) {
                 $("#criteria1").hide();
                 $("#criteria2").hide();
                 $("#criteria3").hide();
                 $("#criteria4").hide();
     			 $("#instrumentGroupId").hide();
    			 $("#instrumentTypeId").hide();
    			 $("#instrumentSubtypeId").hide();
    			 $("#criteria5").show();
    			 $("#criteria6").hide();
              }
             else if(searchType==8) {
                 $("#criteria1").hide();
                 $("#criteria2").hide();
                 $("#criteria3").hide();
                 $("#criteria4").hide();
     			 $("#instrumentGroupId").hide();
    			 $("#instrumentTypeId").hide();
    			 $("#instrumentSubtypeId").hide();
    			 $("#criteria5").hide();
    			 $("#criteria6").show();
              }
          }
       });
