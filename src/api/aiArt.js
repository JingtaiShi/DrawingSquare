import http from './request.js'

export async function submitRequest(parm){
    return http.post("/api/aiArt/submitRequest",parm)
}


export async function getImgUrl(){
    return http.get("/api/aiArt/getImgUrl")
}