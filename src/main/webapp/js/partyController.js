(function () {
    "use strict";
    angular.module("jukeB").controller("partyController",
        ["$scope","Restangular", '$routeParams',"ngDialog",
            "$location","$interval",'$window', '$anchorScroll',

    function($scope, Restangular, $routeParams, ngDialog, $location, $interval, $window, $anchorScroll) {
        $scope.teste = "hello world partyController";
        $scope.teste2 = "h2e2l2l2o2 2w2o2r2ld partyController";


        $scope.gotoPlaying = function() {
            // set the location.hash to the id of
            // the element you wish to scroll to.
            $location.hash('PLAYING');

            // call $anchorScroll()
            $anchorScroll();
        };

        $scope.hideControls = $routeParams.hideControls;

        $scope.hideShowCtrl = function(){
            $location.search('hideControls', !$scope.hideControls);
            $scope.hideControls = !$scope.hideControls;

        }

        $scope.getSongs = function(partyId) {
            var baseParty = Restangular.all('api');
            baseParty.one('party', partyId)
                .getList('songs')
                .then(function (songs) {
                    $scope.musicItemList = songs;
                    console.log('songs');
                    console.log(songs);

                });
        };

        $scope.myStyleFunction = function(song) {
            //console.log('songStatus');
            //console.log(song.songStatus);
            if (song.songStatus == 'PLAYING') {
                return {'color': '#DDDDDD'};
            }
            else{
                return {'left': '300px'};
            }
        }


        $scope.doDelete = function(partyName, id){
            console.log("delete party:" + partyName + " id:" + id);
            ngDialog.open({
                /*controller: function Ctrl(partyId, picId) {
                    console.log("dep:" + partyId);
                    console.log("dep:" + picId);
                    $scope.partyId = partyId;
                    $scope.Id = picId;
                },*/
                controller: "deletePictureController",
                resolve: {
                    partyId: function() {
                        return partyName;
                    },
                    picId : function() {
                        return id;
                    }
                },
                template: 'html/deletePicture.html'
            });
        };

        var slides = $scope.slides = [];
        $scope.currentImageI = 0;
        $interval(function(){
        //    console.log("$scope.pictures.length:" + $scope.pictures.length);
       //     console.log($scope.pictures);
            if($scope.pictures != undefined && $scope.pictures.length > 0) {
                $scope.currentImageI++;
                if ($scope.currentImageI >= $scope.pictures.length)
                    $scope.currentImageI = 0;
                $scope.currentImage = $scope.pictures[$scope.currentImageI]._links.image.href;
                $scope.currentImageId = $scope.pictures[$scope.currentImageI].id;
            }
            //console.log("$scope.currentImage:" + $scope.currentImage);
        }, 10000, 0);

        $scope.$watch(function(){
            return $window.innerWidth;
        }, function(value) {
            console.log(value);
            var max =  (value*0.85);
            if (max > 800) {max = 800}

            $scope.snapOpts = {
                disable: 'right',
                maxPosition: max,
                minPosition: -266,
            };
        });

        $scope.snapOpts = {
            disable: 'right',
            maxPosition: 800,
            minPosition: -266,
        };

        $scope.uploadPicture = function(picture){
            console.log(picture);
            console.log(picture.file);
        };

        var baseParty = Restangular.all('api');

        baseParty.one('party',$routeParams.partyid)
            .get()
            .then(
                function(party){//achou
                    console.log("achou...:" + $routeParams.partyid);
                    console.log(party);
                    $scope.partyName = $routeParams.partyid;

                    baseParty.one('party',$routeParams.partyid).getList("pictures")
                        .then(function (pics) {
                            console.log("pics.length:" + pics.length);
                            if (pics.length <= 0){

                                var initialPic = {"_links":
                                                        {"image":
                                                            {"rel":"image",
                                                             "href":"/images/initial.jpg"},
                                                         "self":
                                                            {"rel":"self",
                                                             "href":"/images/initial.jpg"}
                                                        },
                                                  "uploader":null,
                                                  "party":"samba",
                                                  "id":0};
                                pics.push(initialPic);
                            }

                            $scope.pictures = pics;
                            console.log('dddd');
                            for(var i=0; i <= pics.length ; i++){
                                console.log(pics[i])
                            }
                        });
                        console.log('oi');


                },
                function(res){ //nao achhou
                    console.log("nao achou: " + $routeParams.partyid);
                    console.log(res);

                    ngDialog.open({
                        controller: "createPartyController",
                        template: 'html/partyNotFound.html'
                    });
                 }
            );


        var broker = Stomp.over(new SockJS("/stomp"))
        broker.debug = function (message) {
            		console.log(message)
        }

        broker.connect({}, function () {
            broker.subscribe("/topic/pictures", function (content) {
                //		broker.subscribe("/user/queue/timesheet/patch", function(content) {
                console.log("JSON.parse(content.body)");
                console.log(JSON.parse(content.body));
                $scope.$apply(function () {
                    $scope.pictures = JSON.parse(content.body);
                })
            })
        })

    }]);
})();

