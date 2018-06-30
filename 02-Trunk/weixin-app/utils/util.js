const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : '0' + n
}

const formatQuestionContent = str => {
  // let inputIndex = str.indexOf("(?)")
  //转为数组
  // let questionBodyArray = str.split("")
  //在()前后各加一个$符号
  // questionBodyArray.splice(inputIndex, 0, "$")
  // questionBodyArray.splice(inputIndex + 4, 0, "$")
  //转化为字符串 "100分=$(?)$角"
  // str = questionBodyArray.join("")
  str = str.replace(/\(\?\)/g, "$(?)$")
  let questionBodyArray = str.split("$")
  return questionBodyArray;
}

const turnArrayToAnswerStr = arr => {
  //[undefined,4,undefined,6,undefined] ==>  "4;6"
  let answerStr = ""
  for (let i = 0, arrlen = arr.length; i < arrlen; i++){
    if(arr[i]!=undefined){
      answerStr += ";" + arr[i]
    }
  }
  return answerStr.slice(1)
}

module.exports = {
  formatTime: formatTime,
  formatQuestionContent: formatQuestionContent,
  turnArrayToAnswerStr: turnArrayToAnswerStr
}
