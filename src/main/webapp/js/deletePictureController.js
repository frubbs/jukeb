(function () {
    "use strict";
    angular.module("jukeB").controller("deletePictureController",["$scope","partyId", "picId", "ngDialog","Restangular",
        function($scope, partyId, picId ,ngDialog, Restangular) {
            console.log("depp:" + partyId);
            console.log("depi:" + picId);
            $scope.partyId = partyId;
            $scope.picId = picId;
            var baseParty = Restangular.all('api');
            baseParty.one('party', partyId)
                        .one('pictures', picId)
                        .get()
                        .then(function(pic){
                            $scope.picture = pic;
                    console.log('pic');
                    console.log(pic);
                        });


            $scope.deletePicture = function(partyId, picId) {
                var baseParty = Restangular.all('api');
                console.log('vai');
                baseParty.one('party', partyId)
                    .one('pictures', picId)
                    .remove();

                console.log('bao');
                ngDialog.close();
                ngDialog.open({
                    template: 'html/deletePictureSucess.html'
                });

            };

        }]);
})();
