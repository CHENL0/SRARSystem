MyApp
    .filter('filterName', function() { //可以注入依赖
    return function(name) {
        var prefix = name.split("_")[0];
        var suffix = name.split("_")[1].slice(-6);
        var mergeName = prefix +"..."+suffix;
        return mergeName;
    }
});