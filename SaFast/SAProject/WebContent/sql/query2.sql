select 	sa.NUMEROSCHEDA ||'/'||rc.NUMERORICHIESTA as scheda,
		sa.DATARICHIESTA,
		(select c.DESCCLIENTE from ELENCOCLIENTI c where c.IDCLIENTE = sa.IDCLIENTE) as cliente,
		(select pr.PROCEDURA from PROCEDUR pr where pr.IDPROCEDURA = sa.IDPROCEDURA) as procedura,
		(select p2.PERSONALE from personale p2 where p2.IDPERSONALE = sa.IDPERSONALE) as assegnatario,
		(select p3.PERSONALE from personale p3 where p3.IDPERSONALE =sa.PRESAINCARICO) as presaincarico,
		sa.DATASCADENZA,
		sa.DATACHIUSURA,		
		sa.ANNULLATA,
		rc.DATAANNULLATA,
		/*DBMS_LOB.substr(sa.NOTE, 4000)  as testo,*/	
		rc.CONVALIDA,
		rc.ANNULLATA,
		(select p4.PERSONALE from personale p4 where p4.IDPERSONALE = rc.IDANNULLATORE) as annullatore
from 	SCHEDEANALISI sa,
	 	PERSONALE p,
	 	RICHIESTECLIENTI rc
where	sa.PROGRESSIVO = rc.IDSCHEDAANALISI
and 	sa.IDPROGRAMMATORE = p.IDPERSONALE
and 	p.SIGLA = 'SP'

order by sa.DATARICHIESTA desc