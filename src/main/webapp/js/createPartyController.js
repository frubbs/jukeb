(function () {
    "use strict";
    angular.module("jukeB").controller("createPartyController",["$scope","Restangular", "$location", "ngDialog",
            function($scope, Restangular, $location ,ngDialog) {
                $scope.teste = "hello world partys, sssController";


            $scope.createParty = function(){
                console.log($location.url());
                console.log($location.url().replace('/','').replace('/',''));
                var partyname = $location.url().replace('/','').replace('/','');
                var baseParty = Restangular.all('api/party');
                var newParty = {name: partyname};

                baseParty.post(newParty)
                            .then(function(){$location.path(partyname).replace();});
                ngDialog.close();
                console.log('fechando 1')
            };



        }]);
})();
