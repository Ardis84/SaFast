select 	sa.NUMEROSCHEDA ||'/'||rc.NUMERORICHIESTA as scheda,
		sa.NUMEROSCHEDA ||'_'||rc.NUMERORICHIESTA as identifier,
		sa.DATARICHIESTA,
		(select c.DESCCLIENTE from ELENCOCLIENTI c where c.IDCLIENTE = sa.IDCLIENTE) as cliente,
		(select pr.PROCEDURA from PROCEDUR pr where pr.IDPROCEDURA = sa.IDPROCEDURA) as procedura,
		p.PERSONALE,
		(select p1.personale from personale p1 where p1.idpersonale = rc.IDTECNICO) as tecnico,
		(select p2.personale from personale p2 where p2.idpersonale =ans.idassegnatore) as assegnatore,
		DBMS_LOB.substr(rc.DESCRIZIONE, 4000)  as testo,
		a.ALLEGATO, a.COMMENTO, a.FILESCAN, a.NOMEFILE,
		aa.NOMEFILE as alle
from 	SCHEDEANALISI sa,
	 	PERSONALE p,
	 	RICHIESTECLIENTI rc,
	 	RAPPORTIATTIVITA ra,
	 	ANALISISVOLTE ans,
	 	ALLEGATI a,
	 	ALLEGATIANALISI aa
where	sa.PROGRESSIVO = rc.IDSCHEDAANALISI
and 	sa.IDPROGRAMMATORE = p.IDPERSONALE(+)
and 	sa.numeroscheda = ?
/*and 	nvl(?,rc.NUMERORICHIESTA) = rc.NUMERORICHIESTA*/
and 	ra.idrichieste(+) = rc.IDRICHIESTECLIENTE
and 	ans.IDSCHEDAANALISI = sa.PROGRESSIVO
and 	ans.numeroanalisi = rc.numerorichiesta
and 	a.IDSCHEDAANALISI(+) = sa.PROGRESSIVO
and 	aa.IDSCHEDAANALISI(+) = sa.PROGRESSIVO 
/****/
/*and 	ans.idprogrammatore=107*/
order by sa.DATARICHIESTA desc, sa.NUMEROSCHEDA, rc.NUMERORICHIESTA 