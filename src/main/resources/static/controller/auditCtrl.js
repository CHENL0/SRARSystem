MyApp
    .controller('auditController',['$scope', '$interval','checkService', 'auditService','notifyService','applyService',
        function ($scope,$interval, checkService,auditService,notifyService,applyService) {
        //get username from localStorage
        $scope.pageClass = 'apply';
        $scope.name = localStorage.getItem("data");

        $scope.getApplyInfos = function(){
            auditService.getApplyInfoListData().then(
                function (response) {
                    $scope.applyInfos = response.applyInfos.sort(checkService.compare("applyType"));
                }
            );
        };
        $scope.getApplyInfos();

        $scope.getApplyFileData = function(){
            applyService.getApplyFile().then(
                function (value) {
                    $scope.applyFile = value.applyInfo;
                }
            )
        };
        $scope.getApplyFileData();


        var name =  $scope.name;
        $scope.getOnePfPjInfoListData = function (name){
            checkService.getOnePfPjInfoListData(name).then(
                function (response) {
                    $scope.pjInfoList = response.onePfPjInfoList.sort(checkService.compare("pjStatus"));
                    // $scope.pjInfoList = response.onePfPjInfoList;
                }
            );
        };

        $scope.downloadApplyFile = function (fileName){
            applyService.download().then(
                function (response) {
                    var link = document.createElement('a');
                    var blob = new Blob([response], {type: 'application/zip'});
                    link.setAttribute('href', window.URL.createObjectURL(blob));
                    link.setAttribute('download', fileName );
                    link.style.visibility = 'hidden';
                    document.body.appendChild(link);
                    link.click();
                    document.body.removeChild(link);
                }
            )
        };
            $scope.validateFiles = function () {
                var file = document.getElementById("fileUpload").files[0];
                $scope.file = file;
                if(!file){
                    return;
                }
                var suffix = file.name.split(".")[1];
                var fileSize = file.size;
                if(suffix === "zip" || suffix === "rar"){
                    $scope.fileFromatError = false;
                    if(fileSize < 40000000){
                        $scope.fileSizeError = false;
                    }else {
                        $scope.file = '';
                        $scope.fileSizeError = true;
                    }
                }else {
                    $scope.file = '';
                    $scope.fileFromatError = true;
                }
                $scope.$apply();
                if($scope.file){
                    alert("ok ,the message have finished");
                    auditService.submitApplyFile($scope.file,'applyFile').then(
                        function (response) {
                            if(response.responseType === "SUCCESS"){
                                alert("Congratulations, and your information submitted to success");
                                $scope.getApplyFileData();
                            }else {
                                alert("sorry,your information submitted to error")
                            }
                        }
                    )
                }
            };

       $scope.download = function (applyId, fileName) {
           auditService.download(applyId).then(
               function (response) {
                   var link = document.createElement('a');
                   var blob = new Blob([response], {type: 'application/zip'});
                   link.setAttribute('href', window.URL.createObjectURL(blob));
                   link.setAttribute('download', fileName );
                   link.style.visibility = 'hidden';
                   document.body.appendChild(link);
                   link.click();
                   document.body.removeChild(link);
               }
           )
       };


        $scope.editApplyType = function (applyId,applyUser) {
            $scope.applyIdModal = applyId;
            $scope.applyUserModal = applyUser;
            auditService.getPjType().then(
                function (response) {
                    $scope.pjTypeInfoList = response.pjInfoList;
                }
            )
        };


        $scope.changeApplyType = function (applyId,applyType,applyUser,selectedType) {
            auditService.changeApplyType(applyId,applyType).then(
                function (response) {
                    $scope.getApplyInfos();
                    // var pjNameSplit = pjName.split(".")[0];
                    // notifyService.setNotify($scope.name,pjUser,"Project",pjStatus,pjNameSplit);
                }
            );
            if(applyType ==="2"){
                auditService.createPfInfo(applyUser,selectedType).then(
                    function (response) {
                        // alert("the ")
                        notifyService.setNotify($scope.name,applyUser,"Apply",applyType,response.pfName);
                    }
                )
            }else if(applyType ==="3"){
                notifyService.setNotify($scope.name,applyUser,"Apply",applyType,'');
            }
        };
    }
    ]);

