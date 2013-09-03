#ps1 
md c:\temp
cd c:\temp
echo [DCINSTALL] >>unattend.txt
echo InstallDNS=Yes >>unattend.txt
echo DomainNetBiosName=testdomain >>unattend.txt
echo NewDomainDNSName=testdomain.com >>unattend.txt
echo SiteName=DefaultSite >>unattend.txt
echo ReplicaOrNewDomain=domain >>unattend.txt
echo NewDomain=forest >>unattend.txt
echo ForestLevel=3 >>unattend.txt
echo DomainLevel=3 >>unattend.txt
echo SafeModeAdminPassword= >>unattend.txt
echo RebootOnSuccess=Yes >>unattend.txt
echo c:\windows\system32\dcpromo.exe /unattend:unattend.txt >>addeploy.bat
