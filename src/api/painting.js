import http from './request.js'

export async function addImgApi(parm){
    return http.post("/api/painting/addImgApi",parm)
}

// 获取用户生成的画图列表
export async function getHistoryListApi(parm){
  return await http.get("/api/painting/getHistoryList", parm)
}

// 获取用户购买的画图列表
export async function  getPurchasedPaintingListApi(parm) {
  return await http.get("/api/painting/getPurchasedPaintingList", parm)
}

// 获取用户喜爱的画图列表
export async function  getLikedPaintingListApi(parm) {
  return await http.get("/api/painting/getLikedPaintingList", parm)
}

// 获取gallary的画图列表
export async function getGallaryApi(){
    return await http.get("/api/painting/gallary")
  }
// 获取画图信息
export async function getImgInfo(parm){
    return http.get("/api/painting/getImgInfo",parm)
}

// 搜索图画
export async function searchApi(parm){
  return http.get("/api/painting/searchImage",parm)
}
 
// 将购买的图画添加进购买库中
export async function addImageToLibraryApi(parm){
  return http.post("/api/painting/addImageToLibrary",parm)
}

//喜爱
export async function likeApi(parm){
  return http.post("/api/painting/like",parm)
}

//移除喜爱
export async function removeLikeApi(parm){
  return http.post("/api/painting/remove",parm)
}

//新增画图
export async function addApi(parm){
    return await http.post("/api/painting",parm)
  }
  //获取画图列表
  export async function getListApi(parm){
    return await http.get("/api/painting/list",parm)
  }
  //编辑画图
  export async function editApi(parm){
    return await http.put("/api/painting",parm)
  }
  //删除画图
  export async function deleteApi(parm){
    return await http.delete("/api/painting",parm)
  }