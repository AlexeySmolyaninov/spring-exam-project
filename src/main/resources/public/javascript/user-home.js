function unfollow(id) {
    var unfollowing = {
        id: id
    };

    var httpDeleteFollowing = new XMLHttpRequest();

    httpDeleteFollowing.onreadystatechange = function () {
        if (this.readyState != 4) {
            return;
        }

        response = JSON.parse(this.responseText);
        if (response.goodMsg) {
            var tr = document.querySelector("#account-" + id);
            tr.remove();
            
            var spanFollowingAmount = document.querySelector("#followingAmount");
            var amount = spanFollowingAmount.textContent;
            spanFollowingAmount.textContent = amount == 0 ? 0 : amount - 1;
            
            removeFolloweePosts(id);
        }
    }

    httpDeleteFollowing.open("DELETE", contextRoot + "unfollow");
    httpDeleteFollowing.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpDeleteFollowing.send(JSON.stringify(unfollowing));
}

function newPost(profilename){
    
    var post = {
        text: document.querySelector("#post-text").value
    }
    
    var urlCreatePost = contextRoot + "accounts/" + profilename + "/posts";
    var httpCreatePost = new XMLHttpRequest();
    
    httpCreatePost.onreadystatechange = function() {
        if (this.readyState != 4) {
            return;
        }
        
        var newlyCreatedPost = JSON.parse(this.responseText);
        
        document.querySelector("#post-text").value = "";
        addNewPostToDOM(newlyCreatedPost);
        deleteLastPost();
    }
    
    httpCreatePost.open("POST", urlCreatePost);
    httpCreatePost.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    httpCreatePost.send(JSON.stringify(post));
}

function addNewPostToDOM(post){
    var divPosts = document.querySelector("#posts-container");
    
    var divPost = document.createElement("div");
    divPost.setAttribute("class", "post media border p-3 post-" + post.owner.profileName);
    divPosts.insertBefore(divPost, divPosts.firstChild);
    
    var divMediaBody = document.createElement("media-body");
    divMediaBody.setAttribute("class", "media-body");
    divPost.appendChild(divMediaBody)
    
    var h4Header = document.createElement("h4");
    var aLinkToAccount = document.createElement("a");
    aLinkToAccount.setAttribute("href", "/accounts/" + post.owner.profileName + "");
    h4Header.appendChild(aLinkToAccount);
    
    var spanFirstAndLastName = document.createElement("span");
    spanFirstAndLastName.textContent = post.owner.firstName + " " + post.owner.lastName;
    aLinkToAccount.appendChild(spanFirstAndLastName);
    
    var smallEl = document.createElement("small");
    smallEl.setAttribute("id", "post-date");
    h4Header.appendChild(smallEl);
    
    var iEl = document.createElement("i");
    smallEl.appendChild(iEl);
    
    var spanDate = document.createElement("span");
    iEl.appendChild(spanDate);
    const milSeconds = Date.parse(post.created);
    const dateObj = new Date();
    dateObj.setTime(milSeconds);
    spanDate.appendChild(document.createTextNode(formatToAppDateformat(dateObj.toLocaleString('en-GB', { timeZone: 'Europe/Helsinki' }))));
    
    divMediaBody.appendChild(h4Header);
    
    var pText = document.createElement("p");
    divMediaBody.appendChild(pText);
    pText.appendChild(document.createTextNode(post.text));
    
}

function deleteLastPost(){
    var posts = document.querySelectorAll(".post");
    if(posts.length > 25){
        posts[posts.length - 1].parentNode.removeChild(posts[posts.length - 1]);
    }
}

function formatToAppDateformat(date){
    var resultDate = "";
    var dateParts = date.split("/");
    resultDate = dateParts[0] + "-" + dateParts[1] + "-" + dateParts[2];
    resultDate = resultDate.replace(",", "");
    var dateAndTimeparts = resultDate.split(":");
    resultDate = dateAndTimeparts[0] + ":" + dateAndTimeparts[1];
    return resultDate;
}

function removeFolloweePosts(id){
    var posts = document.querySelectorAll(".post-account-"+id);
    posts.forEach(function (postEl){
    postEl.parentNode.removeChild(postEl);
});
}