window.onload = function(){
  setTimeout(function(){
    var t = performance.timing;
    var loadTime = t.loadEventEnd - t.responseEnd;
    console.log(loadTime);
    
  }, 0);
}