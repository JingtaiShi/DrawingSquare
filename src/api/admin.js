// import request from '@/utils/request'
import http from './request.js'

export async function loginAdminApi(parm){
  return http.post("/api/admin/AdminLogin",parm)
}