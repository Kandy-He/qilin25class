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

//数组去空
const noBlankArr = (arr) => {
  let newArr = arr
  for (let i = 0, arrLen = newArr.length; i < arrLen; i++) {
    if (newArr[i] == " "){
      newArr.splice(i, 1);
      i = i - 1;
    }
  }
  return arr
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
//错题列表页，将错题展示在输入框中
const formatWrongbookArray = (arr,wrongAnswer) => {
  let newArr = arr
  //将原本["100-20=", "(?)"] ==> ["100-20=", "80"]   wrongAnswer: "80;90;100"
  let wrongAnswerArray = wrongAnswer.split(";")
  let startIndex = 0
  for(let i = 0, arrlen = arr.length; i < arrlen; i ++){
    if (arr[i] == "(?)"){
      arr[i] = wrongAnswerArray[startIndex];
      startIndex += 1
    }
  }
  return newArr;
}
const turnArrayToAnswerStr = arr => {
  //[undefined,4,undefined,6,undefined] ==>  "4;6"
  let answerStr = ""
  //当用户没有做任何输入操作
  if (arr.length > 0) {
    for (let i = 0, arrlen = arr.length; i < arrlen; i++) {
      if (arr[i] != undefined) {
        answerStr += ";" + arr[i]
      }
    }
    return answerStr.slice(1)
  }else{
    return answerStr
  }
}
//对题型进行区分，返回三步以及描述题的显示与否的对象
const quesTypeKeyFilter = (questionDetail) => {
  let quesTypeKey = questionDetail.quesTypeKey
  let configStepsObj = {}
  switch (quesTypeKey) {
    //判断三步展示
    case "App-B-Type":
      configStepsObj.showExpression1 = true
      break;
    case "App-C-Type":
      configStepsObj.showExpression1 = true
      configStepsObj.showExpression2 = true
      break;
    case "App-B-Type":
      configStepsObj.showExpression1 = true
      break;
    case "App-D-Type":
      configStepsObj.showExpression1 = true
      configStepsObj.showExpression2 = true
      configStepsObj.showExpression3 = true
      break;
    //判断对错需要显示答题规范
    case "Jud-A-Type":
      configStepsObj.howToAnswer = '请在输入框内输入以下个字符\n“√”、“×”、“对”、“错”'
      break;
  }
  
  //判断是否展示答题描述
  if (questionDetail.answerDesc){
    configStepsObj.showAnswerDesc = true
  }
  return configStepsObj
}

//格式化用户输入答案
const formatAnswerToRightStyle = (answerStr) => {
  let answerArr = answerStr.split("")
  for (let i = 0, answerArrLen = answerArr.length; i < answerArrLen; i ++){
    switch (answerArr[i]) {
      case "对":
        answerArr[i] = "√"
        break;
      case "错":
        answerArr[i] = "×"
        break;
    }
  }
  return noBlankArr(answerArr).join("")
}

//格式化用户输入表达式
const formatExpressionToRightStyle = (expressionStr) => {
  let answerArr = expressionStr.split("")
  for (let i = 0, answerArrLen = answerArr.length; i < answerArrLen; i++) {
    switch (answerArr[i]) {
      case "（":
        answerArr[i] = "("
        break;
      case "）":
        answerArr[i] = ")"
        break;
      case "×":
        answerArr[i] = "*"
        break;
      case "÷":
        answerArr[i] = "/"
        break;
    }
  }
  return noBlankArr(answerArr).join("")
}

module.exports = {
  noBlankArr: noBlankArr,
  formatTime: formatTime,
  formatQuestionContent: formatQuestionContent,
  turnArrayToAnswerStr: turnArrayToAnswerStr,
  formatWrongbookArray: formatWrongbookArray,
  quesTypeKeyFilter: quesTypeKeyFilter,
  formatAnswerToRightStyle: formatAnswerToRightStyle,
  formatExpressionToRightStyle: formatExpressionToRightStyle,

}
