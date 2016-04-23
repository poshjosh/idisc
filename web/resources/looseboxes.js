if (typeof String.prototype.endsWith !== 'function') {
    String.prototype.endsWith = function(suffix) {
        return this.indexOf(suffix, this.length - suffix.length) !== -1;
    };
}

var looseboxes = {
    $:
    function(x) {return looseboxes.getElementById(x);},    
    getElementById:
    function (x){if(document.getElementById) return document.getElementById(x);else if(document.all) return document.all(x); else return null;},
    setNodeValue:
    function (k, v) {
        if(!k) return; //window.alert("#$set. innerHTML:"+k.innerHTML+", nodeValue:"+k.nodeValue+", value:"+k.value); 
        if(k.nodeValue !== null && k.nodeValue !== 'undefined') k.nodeValue = v;
        else if(k.value !== null && k.value !== 'undefined') k.value = v; 
        else if(k.innerHTML !== null && k.innerHTML !== 'undefined') k.innerHTML = v;
    },
    getNodeValue:
    function (k) {if(!k) return null;if(k.innerHTML) return k.innerHTML;else if(k.nodeValue) return k.nodeValue;else if(k.value) return k.value; else return null;},
    addLoadEvent:
    function (func) {
//window.alert("#addLoadEvent. Function: "+func);    
        var oldonload = window.onload;
//window.alert("#addLoadEvent. OLD Function: "+oldonload);    
        if (typeof window.onload != 'function') {
            window.onload = func;
        }else{
            window.onload = function() {
                if (oldonload) {
                    oldonload();
                }
                func();
            };
        }
    },
    //Nested Side Bar Menu (Mar 20th, 09). By Dynamic Drive: http://www.dynamicdrive.com/style/
    initsidebarmenu:
    function (mMenuId) {
        var mMenu = looseboxes.$(mMenuId);
        if(!mMenu) return;
        var ultags=mMenu.getElementsByTagName("ul");
        for (var i=0; i<ultags.length; i++){
            if (ultags[i].parentNode.parentNode.id===mMenuId) //if this is a first level submenu
                ultags[i].style.left=ultags[i].parentNode.offsetWidth+"px"; //dynamically position first level submenus to be width of main menu item
            else //else if this is a sub level submenu (ul)
                ultags[i].style.left=ultags[i-1].getElementsByTagName("a")[0].offsetWidth+"px"; //position menu to the right of menu item that activated it
            ultags[i].parentNode.onmouseover=function(){
                document.getElementsByTagName("ul")[0].style.display="block";
            };
            ultags[i].parentNode.onmouseout=function(){
                document.getElementsByTagName("ul")[0].style.display="none";
            };
        }
        for (var j=(ultags.length-1); j>-1; j--){ //loop through all sub menus again, and use "display:none" to hide menus (to prevent possible page scrollbars
            ultags[j].style.visibility="visible";
            ultags[j].style.display="none";
        }
    },
    addToFav:
    function (title,url){
        if (window.sidebar) {// firefox
            window.sidebar.addPanel(title, url, "");
        }else if(window.opera && window.print){ // opera
            var elem = document.createElement('a');
            elem.setAttribute('href',url);
            elem.setAttribute('title',title);
            elem.setAttribute('rel','sidebar');
            elem.click();
        }else if(document.all) {// ie
            window.external.AddFavorite(url, title);
        }
    },
    loadImage:
    function (url, width, height) {
//window.alert("Loading: "+url);
        looseboxes.loadImageX(url, width, height, null);
    },  
    /** This is it */
    loadImageX:
    function (url, width, height, onloadCallback) {
//window.alert("Loading: "+url);
        var img;
        if(width === null || height === null) {
            img = new Image;
        }else{
            img = new Image(width, height);
        }
        if(onloadCallback) {
            img.onload = function() {
                onloadCallback();    
            };
        }
        img.src = url;
    },    
    loadAndDisplayImage:
    function (width, height, imageNodeId, imageSrc, imageLinkId, imageLink) {
//window.alert("Displaying: "+imageSrc);
        looseboxes.loadImageX(imageSrc, width, height, function(){
            looseboxes.displayImage(imageNodeId, imageSrc, imageLinkId, imageLink);
        });
    },  
    displayImage:
    function (imageNodeId, imageSrc, imageLinkId, imageLink) {
//window.alert("Displaying: "+imageSrc);
        looseboxes.$(imageNodeId).src = imageSrc;
        if(imageLinkId !== null && imageLink !== null) {
            looseboxes.$(imageLinkId).href = imageLink;
        }
    },  
    setVisibility:
    function(elemId, visibilityStyle) {
        if(elemId) {looseboxes.$(elemId).style.visibility = visibilityStyle;}
    },
    setDisplay:
    function(elemId, displayStyle) {
        if(elemId) {looseboxes.$(elemId).style.display = displayStyle;}
    },    
    shareOnFacebook:
    function (link, title) {
        looseboxes.open('http://www.facebook.com/sharer.php?u='+encodeURIComponent(link)+'&t='+encodeURIComponent(title),'Share');
        return false;
    },
    followOnTwitter:
    function (twitterLink, title) {
        looseboxes.open('http://twitter.com/'+twitterLink, title);
        return false;
    },
    tweetItem:
    function (status, title) {
        looseboxes.open('http://twitter.com/home?status='+encodeURIComponent(status), title);
        return false;
    },
    open:
    function (strUrl, strWindowName) {
        window.open(strUrl, strWindowName,'toolbar=0,status=0,width=626,height=436');    
    },
    loadURL:
    function (idToSubmit, url, title, errorURL) {
        myAjax.send("GET", idToSubmit, url, function(ajaxResponse){
            if(ajaxResponse) {
                var output = ajaxResponse.responseText;  
                //@related_41
                if(output === 'error' && errorURL !== null) {
                    // Spaces in title caused errors in IE 9
                    looseboxes.open(errorURL, "Error_Encountered_Loading_URL");
                }else{
                    looseboxes.open(output, title);      
                }  
            }
        });
    },
    // Returns a random integer between min and max
    // Using Math.round() will give you a non-uniform distribution!
    getRandomInt:  
    function (min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
    }        
};

