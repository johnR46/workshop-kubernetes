ดู V ล่าสุด https://github.com/kubernetes/dashboard/releases

แต่ผมจะใช้อันนี้ kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.1.0/aio/deploy/recommended.yaml

เสร็จแล้วต่อด้วย kubectl apply -f dashboard-user.yaml

(powershell)
kubectl -n kubernetes-dashboard describe secret $(kubectl -n kubernet es-dashboard get secret | grep admin-user | awk '{print $1}') 
ดู token

token:      eyJhbGciOiJSUzI1NiIsImtpZCI6IjMyUnFQcXNUOEIzYW9xQTlMdFdwcFBidkMzZUNLdDBzNDBsZ0VxVTBqdkEifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZC10b2tlbi03NjRsNSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6ImQ2ZDMxYTdjLTMyMDUtNDkxNS1hNmU3LTE1MDYzZDkyZmQ3YiIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlcm5ldGVzLWRhc2hib2FyZDprdWJlcm5ldGVzLWRhc2hib2FyZCJ9.kUs9i-N_ifd2ouLtgkq9tk1cVqtTfwQtNsK4PnREUjpU5GKIaQ_4v3dPbP2zINOlzhlalOZqqlHVMZ92x50vjHGvTDTQdIMbxyjSbnJ_OO_FcZmVtGWQYkNAbCi4kRm6-TVVWPI3jBArwMIjuz5XBuUVOatC0WIyFAN3t2e8LoEOvKaaKcIcWBtoq3ZXOjPyWmRrkjaxFCi1UCkOVjdILOcmaAIxs8gilcJI2xZ8nZE_cr3NucxNAHNhtycVMLGY7qneEjFvYtm0wtXQ-JhrKny81YH9fLDEyAEG5nlSaUxxHwbV2TZr68H3eVq7E170z-PVjS2V4lAJi823JCjfyQ


kubectl proxy  
แล้วกดที่ url นี้ http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/
เข้าไป ติ้ดถูกตรง token แล้วเอา tokenที่ได้กรอกไป กด login จบ



Ref(ภาษาไทย) https://www.jittagornp.me/blog/install-kubernetes-dashboard/