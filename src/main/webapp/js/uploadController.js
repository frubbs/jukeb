(function () {
    "use strict";

    angular.module("jukeB").directive('fileModel', ['$parse', function ($parse) {
        return {
            restrict: 'A',
            link: function(scope, element, attrs) {
                var model = $parse(attrs.fileModel);
                var modelSetter = model.assign;

                element.bind('change', function(){
                    scope.$apply(function(){
                        modelSetter(scope, element[0].files[0]);
                    });
                });
            }
        };
    }]);

    angular.module("jukeB").service('fileUpload', ['$http','ngDialog', function ($http) {
        this.uploadFileToUrl = function(file, uploadUrl, ngDialog, $scope){
            var fd = new FormData();
            fd.append('file', file);

            $scope.uploading = true;
            $http.post(uploadUrl, fd, {
                transformRequest: angular.identity,
                headers: {'Content-Type': undefined}
            })
                .success(function(){
                    console.log("foi");
                    $scope.uploading = false;
                    ngDialog.open({
                        template: 'html/uploadSuccess.html'
                    });
                })
                .error(function(){
                    $scope.uploading = false;
                    console.log("NAO foi");
                    ngDialog.open({
                        template: 'html/uploadFail.html'
                    });
                });
        }
    }]);

    angular.module("jukeB").controller('uploadController', ['$scope', 'fileUpload', 'ngDialog',
        function($scope, fileUpload, ngDialog){
        console.log("myfile");
        console.log($scope.myFile);
        $scope.uploadFile = function(partyId){
            var file = $scope.myFile;
            console.log('file is ' );
            console.dir(file);
            var uploadUrl = "/api/party/"+partyId+"/pictures";
            console.log("uploadUrl:" + uploadUrl);
            fileUpload.uploadFileToUrl(file, uploadUrl, ngDialog, $scope);
            $scope.myFile = undefined;
        };

        $scope.fileSet = function(){
            return $scope.myFile != undefined;
        }


    }]);
})();


