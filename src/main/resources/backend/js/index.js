function trim (str) {
  return str == undefined ? "" : str.replace(/(^\s*)|(\s*$)/g, "")
}

function requestUrlParam(argname){
  const url = location.href
  const arrStr = url.substring(url.indexOf("?")+1).split("&")
  for(let i =0;i<arrStr.length;i++)
  {
      const loc = arrStr[i].indexOf(argname+"=")
      if(loc!=-1){
          return arrStr[i].replace(argname+"=","").replace("?","")
      }
  }
  return ""
}
