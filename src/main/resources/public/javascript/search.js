//after load
var myAccount;
var myFollowings
window.onload = function() {
    var httpMyDetails = new XMLHttpRequest();
    httpMyDetails.open("GET", contextRoot + "myDetails");
    httpMyDetails.send();

    httpMyDetails.onreadystatechange = function() {
        if(this.readyState != 4 || this.status != 200){
             return;
        }
        myAccount = JSON.parse(this.responseText);
     }
     
};



var urlFindAccounts = contextRoot + "findAccounts";

var httpFindAccounts = new XMLHttpRequest();

httpFindAccounts.onreadystatechange = function(){
    if(this.readyState != 4 || this.status != 200){
        return;
    }
    
    response = JSON.parse(this.responseText);
    var divSearchResult = document.getElementById("search-result");
    var header = document.createElement("h1");
    header.appendChild(document.createTextNode("Result:"));
    divSearchResult.appendChild(header);
    
    if(response.length == 0){
        var paragraph = document.createElement("p");
        paragraph.appendChild(document.createTextNode("We haven't found no one!"));
        divSearchResult.appendChild(paragraph);
    }
    
    for(var i = 0; i < response.length; i++){
        var divCard = document.createElement("div");
        divCard.setAttribute("class", "card")
        
        var divCardBody = document.createElement("div");
        divCardBody.setAttribute("class","card-body");
        
        var nameElement = document.createElement("h4");
        nameElement.setAttribute("class", "card-title");
        nameElement.appendChild(document.createTextNode(response[i].firstName + " " + response[i].lastName));
        
        var aSeeProfile = document.createElement("a");
        aSeeProfile.setAttribute("class", "btn btn-primary");
        aSeeProfile.setAttribute("href", "/accounts/" + response[i].profileName);
        aSeeProfile.appendChild(document.createTextNode("See Profile"));
        
        
        divCardBody.appendChild(nameElement);
        divCardBody.appendChild(aSeeProfile);
        
        divCard.appendChild(divCardBody);
        
        divSearchResult.appendChild(divCard);
        
        var canFollow = true;
        myFollowings.forEach(function(following){
            if(following.id == response[i].id){
                canFollow = false;
                var aUnfollow = document.createElement("button");
                aUnfollow.setAttribute("class", "btn btn-danger");
                aUnfollow.setAttribute("onclick", "unfollow("+ response[i].id +")");
                aUnfollow.appendChild(document.createTextNode("Unfollow"));
                
                divCardBody.appendChild(aUnfollow);
                return;
            }
        });
        
        if(canFollow){
            var aFollow = document.createElement("button");
                aFollow.setAttribute("class", "btn btn-success");
                aFollow.setAttribute("onclick", "follow("+ response[i].id + ")");
                aFollow.appendChild(document.createTextNode("Follow"));
                
                divCardBody.appendChild(aFollow);
        }
    }
}

function search(){
    
    var httpMyFollowings = new XMLHttpRequest();
    var urlMyFollowings = contextRoot + "accounts/"+ myAccount.profileName + "/followings";
    httpMyFollowings.open("GET", urlMyFollowings);
    httpMyFollowings.send();
    httpMyFollowings.onreadystatechange = function(){
        if(this.readyState != 4 || this.status != 200){
         return;
        }
        myFollowings = JSON.parse(this.responseText);
        continueSearch();
    }
    
}

function continueSearch(){
    var divSearchResult = document.getElementById("search-result");
    while (divSearchResult.firstChild) {
        divSearchResult.removeChild(divSearchResult.firstChild);
    }
    
    "?firstname=&lastname";
    var explicitUrlFindAccounts = urlFindAccounts + 
        "?firstname=" + document.getElementById("first-name").value +
        "&lastname=" + document.getElementById("last-name").value;

    httpFindAccounts.open("GET", explicitUrlFindAccounts);
    httpFindAccounts.send();
}

function follow(id){
    var following = {
        id: id
    };
    
    var httpFollow = new XMLHttpRequest();
    
    httpFollow.onreadystatechange = function() {
        if(this.readyState != 4){
            return;
        }
        
        response = JSON.parse(this.responseText);
        if(response.goodMsg){
            var buttonFollow = document.querySelector("[onclick='follow("+ id +")']");
            
            buttonFollow.setAttribute("class", "btn btn-danger");
            buttonFollow.setAttribute("onclick", "unfollow("+ id +")");
            buttonFollow.textContent = "Unfollow";
        }
    }
    
    httpFollow.open("POST", contextRoot + "follow");
    httpFollow.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpFollow.send(JSON.stringify(following));
    
}

function unfollow(id){
    var unfollowing = {
        id: id
    };
    
    var httpDeleteFollowing = new XMLHttpRequest();
    
    httpDeleteFollowing.onreadystatechange = function() {
        if(this.readyState != 4){
            return;
        }
        
        response = JSON.parse(this.responseText);
        if(response.goodMsg){
            var buttonUnfollow = document.querySelector("[onclick='unfollow("+ id +")']");
            
            buttonUnfollow.setAttribute("class", "btn btn-success");
            buttonUnfollow.setAttribute("onclick", "follow("+ id +")");
            buttonUnfollow.textContent = "Follow";
        }
    }

    httpDeleteFollowing.open("DELETE", contextRoot + "unfollow");
    httpDeleteFollowing.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpDeleteFollowing.send(JSON.stringify(unfollowing));
}




