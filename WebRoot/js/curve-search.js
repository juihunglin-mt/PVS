       $(document).ready(function() {
          changeCriteria();
          $("#searchType").change(changeCriteria);
          
          $("#submitbtn").click(function() {
             var searchType = $("#searchType").val();
             if(searchType==1 && $("#identifier").val()=="") {
                alert("please input type");
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
             }
             else if(searchType==1) {
                $("#criteria1").show();
                $("#criteria2").hide();
             }
             else if(searchType==2) {
                $("#criteria1").hide();
                $("#criteria2").show();
             
             }
          }
       });
