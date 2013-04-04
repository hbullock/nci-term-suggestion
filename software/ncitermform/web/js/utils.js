//----------------------------------------------------------------------------//
// Note: The Firefox browser is initial configure to display a URL
//   within a separate tab when calling the window.open method.
//   To configure Firefox display within a separate window:
//     * Start up Firefox,
//     * Select Menubar --> Tools --> Options,
//     * Select "Tabs" tab,
//     * Select "a new window" radio button.
//----------------------------------------------------------------------------//
function displayLinkInNewWindow(id) {
  var element = document.getElementById(id);
  var url = element.value;
  if (url != "")
    element.onclick=window.open(url);
  else
	alert("URL not set.");
}

function displayVocabLinkInNewWindow(id) {
  var element = document.getElementById(id);
  var url = element.value;
  if (url != "")
    element.onclick=window.open(url);
  else
	alert("This vocabulary does not have\nan associated home page.");
}

function go(loc) {
  window.location.href = loc;
}


//----------------------------------------------------------------------------//
function closeWindow() {
  window.open('','_parent','');
  window.close();
}


   function check_blank()
   {
      var email=document.getElementById("email").value;  
      if (email == "")
      {
          alert("Please provide an email address.");
          {
              document.getElementById("email").focus();
          } 
          return false;
      }   
   
      var term=document.getElementById("term").value;  
      if (term == "")
      {
          alert("Please enter a term.");
          {
              document.getElementById("term").focus();
          }        
          return false;
      } 
      
      var answer=document.getElementById("answer").value;
      var captcha_option = document.getElementById("captcha_option").value;
      if (answer == "")
      {
          if (captcha_option == "default") {
              alert("Please enter the characters appearing in the image.");
          } else {
              alert("Please enter the digits you heard from the audio.");
          } 
          document.getElementById("answer").focus();
          return false;
      }

      return true;
   }
   

   function clear_form()
   {
      if (confirm("Are you sure you want to clear form?")) {
	    document.suggestion.reset();
	    
	    //document.getElementById("email") = "";
	    //document.getElementById("other") = "";
	    //document.getElementById("term") = "";
	    //document.getElementById("synonyms") = "";
	    //document.getElementById("code") = "";
	    //document.getElementById("definition") = "";
	    //document.getElementById("project") = "";
	    //document.getElementById("reason") = "";
	    //document.getElementById("cadsrSource") = "";
	    //document.getElementById("cadsrType") = "";
	    //document.getElementById("answer") = "";

      }
      return false;
   }


   function clear_cdisc_form()
   {
      if (confirm("Are you sure you want to clear form?")) {
	    document.suggestion.reset();
    
	    //document.getElementById("email") = "";
	    //document.getElementById("name") = "";
	    //document.getElementById("phone") = "";
	    //document.getElementById("organization") = "";
	    //document.getElementById("other") = "";
	    //document.getElementById("term") = "";
	    //document.getElementById("reason") = "";
	    //document.getElementById("answer") = "";

      }
      return false;
   }
   
   

   function try_again()
   {
       if(navigator.appName == "Microsoft Internet Explorer")
       {
      	    parent.history.back(); 
       }    
       return false;
   }
   
   function reloadPage()
   {
       window.location.reload();
   }

function confirmClear() {
	var agree=confirm("Are you sure you want to clear this form?");
	if (agree)
	     return true;
	else
	     return false;
	}
}

   
function handleSubmit(event, submitId) {

  var keycode;
  //var keyCode = (window.event) ? event.which : event.keyCode;

  if (window.event) keycode = window.event.keyCode;
  else if (event) keycode = event.which;

  if (keycode == 13 &&
      event.target.nodeName == 'INPUT' &&
      event.target.getAttribute('type') == 'text') { 
	document.getElementById(submitId).click();
	return false;
  }
}   