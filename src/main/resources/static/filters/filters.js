MyApp
    .filter('filterName', function() { //可以注入依赖
    return function(name) {
        if(name){
            var prefix = name.split("_")[0];
            var suffix = name.split("_")[1].slice(-6);
            var mergeName = prefix +"..."+suffix;
            return mergeName;
        }
        return;
    }

})
.filter('filterTilte', function() { //可以注入依赖
    return function(name) {
        if(name){
            if(name.length>15){
                var prefix = name.slice(0,5);
                var suffix = name.slice(-6);
                var mergeName = prefix +"..."+suffix;
                return mergeName;
            }
            return name;
        }
        return;
    }

});
