
function isValidUsername (str) {
  return ['admin', 'editor'].indexOf(str.trim()) >= 0;
}

function isExternal (path) {
  return /^(https?:|mailto:|tel:)/.test(path);
}

function isCellPhone (val) {
  if (!/^[2-9]\d{9}$/.test(val)) {
    return false
  } else {
    return true
  }
}

function checkUserName (rule, value, callback){
  if (value == "") {
    callback(new Error("Input username"))
  } else if (value.length > 20 || value.length <3) {
    callback(new Error("Length at least 3-20"))
  } else {
    callback()
  }
}

function checkName (rule, value, callback){
  if (value == "") {
    callback(new Error("Input name"))
  } else if (value.length > 12) {
    callback(new Error("Length at least 1-12"))
  } else {
    callback()
  }
}

function checkPhone (rule, value, callback){
  if (value == "") {
    callback(new Error("Input phone number"))
  } else if (!isCellPhone(value)) {
    callback(new Error("Input valid phone number"))
  } else {
    callback()
  }
}


function validID (rule,value,callback) {
  let reg = /(^\d{10}$)/
  if(value == '') {
    callback(new Error('Input SSN'))
  } else if (reg.test(value)) {
    callback()
  } else {
    callback(new Error('Length should be 10'))
  }
}