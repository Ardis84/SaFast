select 	sa.DATARICHIESTA,
		sa.DATASCADENZA,
		sa.DATACHIUSURA,
		(select c.DESCCLIENTE from ELENCOCLIENTI c where c.IDCLIENTE = sa.IDCLIENTE) as cliente,
		(select pr.PROCEDURA from PROCEDUR pr where pr.IDPROCEDURA = sa.IDPROCEDURA) as procedura,
		sa.NUMEROSCHEDA ||'/'||rc.NUMERORICHIESTA as scheda,
		sa.ANNULLATA,
		rc.DATAANNULLATA,
		DBMS_LOB.substr(sa.NOTE, 4000)  as testo,
		(select p2.PERSONALE from personale p2 where p2.IDPERSONALE = sa.IDPERSONALE) as assegnatario,
		(select p3.PERSONALE from personale p3 where p3.IDPERSONALE =sa.PRESAINCARICO) as presaincarico,
		rc.CONVALIDA,
		rc.ANNULLATA,
		rc.IDANNULLATORE
from 	SCHEDEANALISI sa,
	 	PERSONALE p,
	 	RICHIESTECLIENTI rc
where	sa.PROGRESSIVO = rc.IDSCHEDAANALISI
and 	sa.IDPROGRAMMATORE = p.IDPERSONALE
and 	p.SIGLA = 'SP'