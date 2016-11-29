select 	sa.progressivo as idschedeanalisi,
		sa.NUMEROSCHEDA ||'/'||rc.NUMERORICHIESTA as scheda,
		sa.NUMEROSCHEDA ||'_'||rc.NUMERORICHIESTA as identifier,
		sa.DATARICHIESTA,
		(select c.DESCCLIENTE from ELENCOCLIENTI c where c.IDCLIENTE = sa.IDCLIENTE) as cliente,
		(select pr.PROCEDURA from PROCEDUR pr where pr.IDPROCEDURA = sa.IDPROCEDURA) as procedura,
		p.PERSONALE,
		(select p1.personale from personale p1 where p1.idpersonale = rc.IDTECNICO) as tecnico,
		(select p2.personale from personale p2 where p2.idpersonale =ans.idassegnatore) as assegnatore,
		DBMS_LOB.substr(sa.NOTE, 4000)  as note,
		DBMS_LOB.substr(rc.DESCRIZIONE, 4000)  as testo,
		rc.ANNULLATA,
		DBMS_LOB.substr(np.NOTA, 4000)  as notesviluppo,
		DBMS_LOB.substr(ra.NOTE, 4000)  as noterapporto,
		(select count(*) from allegati al where al.IDSCHEDAANALISI = sa.progressivo) as numallegati,
		ans.CONVALIDADATAORA
from 	SCHEDEANALISI sa,
	 	PERSONALE p,
	 	RICHIESTECLIENTI rc,
	 	RAPPORTIATTIVITA ra,
	 	ANALISISVOLTE ans,
	 	NOTEPROGRAMMATORI np
where	sa.PROGRESSIVO = rc.IDSCHEDAANALISI
and 	sa.IDPROGRAMMATORE = p.IDPERSONALE(+)
and 	ra.IDRICHIESTE(+) = rc.IDRICHIESTECLIENTE
and 	ans.IDSCHEDAANALISI = sa.PROGRESSIVO
and 	ans.numeroanalisi = rc.numerorichiesta
and 	rc.ANNULLATA = 'N'
and 	np.IDRICHIESTA(+) = rc.IDRICHIESTECLIENTE
and 	ans.CONVALIDADATAORA is null
/****/
/****/
and 	(ans.idprogrammatore=? or ra.IDPERSONALE = ?)
order by sa.DATARICHIESTA desc, sa.NUMEROSCHEDA, rc.NUMERORICHIESTA 

